package MessageHandler;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IMessageHandler {

    public void sendMessage(Channel channel,String message, String user) throws IOException, TimeoutException;
    public void postMessage(Channel channel, String message, String user) throws IOException, TimeoutException;
    public void postTopic(Channel channel, String message, String user) throws IOException, TimeoutException;
    public String getMessage(Channel channel) throws IOException, TimeoutException;
}