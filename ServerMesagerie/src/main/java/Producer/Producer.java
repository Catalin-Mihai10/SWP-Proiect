package Producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Producer implements IProducer{
    private MessageHandler messageHandler;
    private Connection producerConnection;
    private Channel producerChannel;

    public Producer() throws IOException, TimeoutException {
        messageHandler = new MessageHandler();
        ConnectionFactory producerFactory = new ConnectionFactory();
        producerFactory.setHost(Constants.LOCAL_HOST);

        producerConnection = producerFactory.newConnection();
        producerChannel = producerConnection.createChannel();
    }

    @Override
    public String sendMessage(ConstructMessage constructMessage){
        return messageHandler.sendMessage(producerChannel, constructMessage);
    }

    @Override
    public void closeConnection() throws IOException, TimeoutException {
        producerChannel.close();
        producerConnection.close();
    }

}