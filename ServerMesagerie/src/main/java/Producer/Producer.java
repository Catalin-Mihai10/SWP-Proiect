package Producer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Producer implements IProducer{
    //private MessageHandler messageHandler;

    public Producer(){
    }

    public void sendMessage(String message, String user) throws IOException, TimeoutException{
        ConnectionFactory producerFactory = new ConnectionFactory();
        producerFactory.setHost("localhost");
        try(Connection producerConnection = producerFactory.newConnection()){
            Channel producerChannel = producerConnection.createChannel();
            producerChannel.queueDeclare(user, false, false, false, null);
            //messageHandler.sendMessage(this.producerChannel, message, user);
        }
    }
}