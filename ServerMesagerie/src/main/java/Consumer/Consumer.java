package Consumer;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

class Consumer implements IConsumer{
    //private MessageHandler messageHandler;

    private Connection consumerConnection;
    private Channel consumerChannel;
    private String message = "";

    public Consumer() {
        ConnectionFactory consumerFactory = new ConnectionFactory();
        consumerFactory.setHost("localhost");

        consumerConnection = consumerFactory.newConnection();
        consumerChannel = consumerConnection.createChannel();
    }

    public String getMessage(){
        //this.message = messageHandler.getMessage();
        return message;
    }

    @Override
    public void closeConnection() throws IOException{
        consumerConnection.close();
    }
}