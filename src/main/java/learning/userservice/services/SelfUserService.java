package learning.userservice.services;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learning.userservice.dtos.SendEmailEventDto;
import learning.userservice.exceptions.*;
import learning.userservice.models.Token;
import learning.userservice.models.User;
import learning.userservice.models.UserAddress;
import learning.userservice.repositories.TokenRepository;
import learning.userservice.repositories.UserAddressRepository;
import learning.userservice.repositories.UserRepository;
import learning.userservice.repositories.projections.SignupUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

@Service("selfUserService")
public class SelfUserService implements UserService{
    UserRepository userRepository;
    UserAddressRepository userAddressRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    @Autowired
    public SelfUserService(UserRepository userRepository,
                           UserAddressRepository userAddressRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository,
                           KafkaTemplate<String, String> kafkaTemplate,
                           ObjectMapper objectMapper){
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;

    }


    @Override
    public User signup(String firstName, String lastName, String email, String password) throws UserAlreadyExistsException, JsonProcessingException {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("user with email " + email + " already exists.");
        }
        user  = userRepository.save(user);

        //emailDto
        SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();
        sendEmailEventDto.setTo(email);
        sendEmailEventDto.setFrom("abhinavrgupta98@gmail.com ");
        sendEmailEventDto.setSubject("Welcome to Ecomm  Webapp by Abhinav");
        sendEmailEventDto.setBody(
                "Thank you for signing up!" +
                        "Looking forward to your great shopping  experience."+
                        "\n Regards,\nAbhinav Gupta"
        );

        kafkaTemplate.send(
                "sendEmail",
                objectMapper.writeValueAsString(sendEmailEventDto)
        );
        return user;
    }

    @Override
    public Token login(String email, String password) throws UserNotFoundException, InvalidFieldException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("Invalid email");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            // throw exception
            throw new InvalidFieldException("Invalid Password!");
        }
        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        return tokenRepository.save(token);
    }

    @Override
    public void logout(String token) throws TokenNotFoundOrExpiredException {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(token, false);
        if(tokenOptional.isEmpty()){
            throw new TokenNotFoundOrExpiredException("token not found or expired");
        }
        Token token1 = tokenOptional.get();
        token1.setDeleted(true);
        tokenRepository.save(token1);
        return;
    }

    @Override
    public User validateToken(String token) throws TokenNotFoundOrExpiredException {
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(token, false);
        if(tokenOptional.isEmpty()){
            throw new TokenNotFoundOrExpiredException("token not found. PLease login again");
        }
        return tokenOptional.get().getUser();
    }


    @Override
    public User getUser(Long id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User with id: "+ id + " not found.");
        }

        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) throws UserAlreadyExistsException, EmptyRequiredFieldException {
        // check mandatory fields
        if((user.getUsername()==null) || (user.getEmail() == null) || (user.getHashedPassword()== null)){
            throw new EmptyRequiredFieldException("email/username/password cannot be empty");
        }

        //check if user already exists
        Optional<User> userOptional = userRepository.
                findByEmailOrUsername(user.getEmail(), user.getUsername());
        if(userOptional.isPresent()){
            if(userOptional.get().getEmail().equals(user.getEmail())){
                throw new UserAlreadyExistsException("User with emailId: " + user.getEmail() + " already exists.");
            }
            else if(userOptional.get().getUsername().equals(user.getUsername())){
                throw new UserAlreadyExistsException("User with username: " + user.getUsername() + " already exists.");
            }
        }
        // necessary step to set user_id(FK) in address table
        for(UserAddress address : user.getUserAddresses()){
            address.setUser(user);
        }
        return userRepository.save(user);

    }

    @Override
    public User updateUser(Long id, User updateUser) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException("user with id: " + id + " not found.");
        }
        User user = userOptional.get();
        user.setFirstName(updateUser.getFirstName() != null ? updateUser.getFirstName() : user.getFirstName());
        user.setLastName(updateUser.getLastName() != null ? updateUser.getLastName() : user.getLastName());
        user.setHashedPassword(updateUser.getHashedPassword() != null ? updateUser.getHashedPassword() : user.getHashedPassword());
        user.setUsername(updateUser.getUsername() != null ? updateUser.getUsername() : user.getUsername());
        user.setPhone(updateUser.getPhone() != null ? updateUser.getPhone() : user.getPhone());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
