package Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IConsumer{
    void getMessage() throws IOException, TimeoutException;
    void subscribeToTopic();
    void closeConnection() throws IOException, TimeoutException;
}