package UserManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public interface UserManagerInterface {
    boolean addUser(String user_) throws IOException, ParseException;
    boolean removeUser(String user_) throws IOException, ParseException;
    //void sendSignal(String user_, boolean signal);

}