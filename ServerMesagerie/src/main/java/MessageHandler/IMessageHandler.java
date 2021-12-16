package MessageHandler;

import java.nio.channels.Channel;
import ConstructMessage.ConstructMessage;

interface IMessageHandler {

    public void sendMessage(Channel channel, ConstructMessage constructMessage);
    public void postTopic(Channel channel, String message, String queueName);
}