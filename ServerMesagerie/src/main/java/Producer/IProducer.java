package Producer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import ConsctructMessage.ConstructMessage;

interface IProducer{
    public String sendMessage(ConstructMessage constructMessage) throws IOException, TimeoutException;
    public void closeConnection() throws IOException, TimeoutException;
}