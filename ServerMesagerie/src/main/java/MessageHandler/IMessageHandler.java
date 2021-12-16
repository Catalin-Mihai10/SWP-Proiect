package MessageHandler;

import Utilitati.ConstructMessage;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

interface IMessageHandler {

    String sendMessage(Channel channel, ConstructMessage constructMessage) throws IOException, TimeoutException;
    String postMessage(Channel channel, ConstructMessage constructMessage) throws IOException, TimeoutException;
    String postTopic(Channel channel, String message, String user) throws IOException, TimeoutException;
}