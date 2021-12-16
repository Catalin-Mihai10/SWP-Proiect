package UserManager;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public interface UserManagerInterface {
    boolean addUser(String user_) throws IOException, ParseException, java.text.ParseException;
    boolean removeUser(String user_) throws IOException, ParseException, java.text.ParseException;
    List<String> getUsers() throws IOException, ParseException;
}