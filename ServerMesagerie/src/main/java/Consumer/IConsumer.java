package Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IConsumer{
    public Sring getMessage();
    public void closeConnection() throws IOException;
}