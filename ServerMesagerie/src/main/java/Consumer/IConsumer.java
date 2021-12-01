package Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IConsumer{
    public String getMessage() throws IOException, TimeoutException;
    public void closeConnection() throws IOException;
}