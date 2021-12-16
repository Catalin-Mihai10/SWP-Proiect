package Producer;
import Utilitati.ConstructMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IProducer{
    String sendMessage(ConstructMessage constructMessage) throws IOException, TimeoutException;
    void closeConnection() throws IOException, TimeoutException;
}