package Consumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import MessageHandler.MessageHandler;

public class Consumer implements IConsumer{
    private MessageHandler messageHandler;
    private String queueName;

    private Connection consumerConnection;
    private Channel consumerChannel;
    private String message = "";

    public Consumer() throws IOException, TimeoutException{
        queueName = "";
        ConnectionFactory consumerFactory = new ConnectionFactory();
        consumerFactory.setHost("localhost");

        consumerConnection = consumerFactory.newConnection();
        consumerChannel = consumerConnection.createChannel();
        consumerChannel.queueDeclare(queueName, false, false, false, null);
    }

    public String getMessage() throws IOException, TimeoutException{
        return messageHandler.getMessage(consumerChannel);
    }

    @Override
    public void closeConnection() throws IOException{
        consumerConnection.close();
    }

    public Channel getConsumerChannel(){
        return consumerChannel;
    }

    public void setMessageHandler(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    public void setQueueName(String queueName){
        this.queueName = queueName;
    }
}