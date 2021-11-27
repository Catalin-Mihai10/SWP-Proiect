package Producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Producer implements IProducer{
    //private MessageHandler messageHandler;

    private Connection producerConnection;
    private Channel producerChannel;

    public Producer() throws IOException, TimeoutException {
        ConnectionFactory producerFactory = new ConnectionFactory();
        producerFactory.setHost("localhost");

        producerConnection = producerFactory.newConnection();
        producerChannel = producerConnection.createChannel();
    }

    @Override
    public void sendMessage(String message, String user) throws IOException, TimeoutException{
            //messageHandler.sendMessage(producerChannel, message, user);
    }

    @Override
    public void closeConnection() throws IOException{
        producerConnection.close();
    }
}