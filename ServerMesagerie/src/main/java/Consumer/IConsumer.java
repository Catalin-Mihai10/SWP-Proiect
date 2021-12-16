package Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IConsumer{
    String getMessage();
    void closeConnection() throws IOException, TimeoutException;
}