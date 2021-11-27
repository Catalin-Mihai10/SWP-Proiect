package Producer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IProducer{
    public void sendMessage(String message, String user) throws IOException, TimeoutException;
    public void closeConnection() throws IOException;
}