package Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IConsumer{
    public String getMessage();
    public void closeConnection() throws IOException;
}