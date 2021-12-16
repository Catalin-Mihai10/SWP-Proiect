package Exceptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String user){
        super("Utilizatorul:" + user + "exista deja!");
    }
}
