package Producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import MessageHandler.MessageHandler;

public class Producer implements IProducer{
    private MessageHandler messageHandler;
    private Connection producerConnection;
    private Channel producerChannel;
    private String queueName;

    public Producer() throws IOException, TimeoutException {
        queueName = "";

        ConnectionFactory producerFactory = new ConnectionFactory();
        producerFactory.setHost("localhost");

        producerConnection = producerFactory.newConnection();
        producerChannel = producerConnection.createChannel();
    }

    @Override
    public void sendMessage(String message, String user) throws IOException, TimeoutException{
        messageHandler.sendMessage(producerChannel, message, queueName);
    }

    @Override
    public void closeConnection() throws IOException{
        producerConnection.close();
    }

    public void setMessageHandler(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    public void setQueueName(String queueName){
        this.queueName = queueName;
    }
}