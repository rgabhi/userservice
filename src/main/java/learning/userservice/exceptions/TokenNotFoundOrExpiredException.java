package learning.userservice.exceptions;

public class TokenNotFoundOrExpiredException extends Exception{
    public TokenNotFoundOrExpiredException(String e){
        super(e);
    }

}
