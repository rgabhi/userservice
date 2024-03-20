package learning.userservice.controlleradvices;

import learning.userservice.dtos.ExceptionDto;
import learning.userservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler
    public ResponseEntity<ExceptionDto> HandleUserAlreadyExistsException(UserAlreadyExistsException e){
        ExceptionDto exceptionDto =new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionDto> HandleEmptyRequiredFieldException(EmptyRequiredFieldException e){
       ExceptionDto exceptionDto = new ExceptionDto();
       exceptionDto.setMessage(e.getMessage());
       return  new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> HandleUserNotFoundException(UserNotFoundException e){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> HandleInvalidFieldException(InvalidFieldException e){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> HandleTokenNotFoundOrExpiredException(TokenNotFoundOrExpiredException e){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

}
