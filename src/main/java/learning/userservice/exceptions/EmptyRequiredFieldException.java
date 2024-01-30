package learning.userservice.exceptions;

public class EmptyRequiredFieldException extends Exception{

    public EmptyRequiredFieldException(String message){
        super(message);
    }

}
