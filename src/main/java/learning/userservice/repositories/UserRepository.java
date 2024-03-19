package learning.userservice.repositories;

import learning.userservice.models.User;
import learning.userservice.repositories.projections.SignupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmailOrUsername(String email, String username);
    public Optional<User> findByEmail(String email);
}
