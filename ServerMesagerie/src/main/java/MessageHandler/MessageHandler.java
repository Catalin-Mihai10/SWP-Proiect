package MessageHandler;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageHandler implements IMessageHandler{

    private enum messageTypes {
        TOPIC, PRIVATE, UNDEFINED
    }

    private String message;
    private String queueName;
    private messageTypes type;
    private QueueManager queueManager;

    public MessageHandler() {
        message = "";
        queueName = "";
        type = messageTypes.UNDEFINED;
        queueManager = new QueueManager();
    }

    @Override
    public void sendMessage(Channel channel, String message, String queueName) throws IOException, TimeoutException{
        this.message = message;
        this.queueName = queueName;

        if(this.queueName == "topic") type = messageTypes.TOPIC;
        type = messageTypes.PRIVATE;

        HandleMessageType(channel, message, queueName);
    }

    private void HandleMessageType(Channel channel, String message, String queueName) throws IOException, TimeoutException{

        switch(type){
            case TOPIC:
                 postTopic(channel, message, "topic");
                 break;
            case PRIVATE:
                 if(verifyQueue()) postMessage(channel, message, queueName);
                 break;
            default:
                System.out.println("ERROR: UNDEFINED MESSAGE TYPE");
                break;
        }
    }

    //TODO: think about posting Topics
    @Override
    public void postTopic(Channel channel, String message, String queueName) throws IOException, TimeoutException{
        String topicExchangeName = "topic-exchange";

        try {
            ifQueueDoesNotExistCreateIt(channel, queueName, topicExchangeName);

            channel.basicPublish("", queueName, false, null, message.getBytes());
            System.out.println("Topic published susccesfully!");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void postMessage(Channel channel, String message, String queueName) throws IOException, TimeoutException{
        String messageExchangeName = "message-exchange";

        try {
            ifQueueDoesNotExistCreateIt(channel, queueName, messageExchangeName);

            channel.basicPublish("", queueName, false, null, message.getBytes());
            System.out.println("Message sent susccesfully!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean verifyQueue() {
        boolean response = queueManager.checkQueue(message);
        return response;
    }

    @Override
    public String getMessage(Channel channel) throws IOException, TimeoutException{
        return channel.basicConsume(queueName, true, (consumerTag, message) -> {
            String receivedMessage = new String(message.getBody(), "UTF-8");
            System.out.println("Message received" + receivedMessage);
        }, consumerTag -> {});
    }


    private void ifQueueDoesNotExistCreateIt(Channel channel, String queueName, String exchangeName) throws IOException {
        try {
            channel.exchangeDeclarePassive(exchangeName);
            if(channel.queueDeclarePassive(queueName).getMessageCount() > 0){
                channel.queueBind(queueName, exchangeName, "");
            }
        }catch(IOException e){
            channel.queueDeclare(queueName, false, false, false, null);
        }
    }
}