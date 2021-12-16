package Consumer;

package Consumer;

import Utilitati.Constants;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.DeliverCallback;

public class Consumer implements IConsumer{
    private String queueName;
    private Connection consumerConnection;
    private Channel consumerChannel;

    public Consumer(String user) throws IOException, TimeoutException{
        queueName = user;
        ConnectionFactory consumerFactory = new ConnectionFactory();
        consumerFactory.setHost(Constants.LOCAL_HOST);

        consumerConnection = consumerFactory.newConnection();
        consumerChannel = consumerConnection.createChannel();
    }

    public void getMessage() {
        Thread thread = new Thread(() -> {
            try {
                consumerChannel.queueDeclare(queueName, false, false, false, null);

                consumerChannel.basicConsume(queueName, true, (consumerTag, message) -> {
                    String receivedMessage = new String(message.getBody(), StandardCharsets.UTF_8);
                    System.out.println(receivedMessage);
                }, consumerTag -> {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(true){}
        });

        thread.start();
    }

    public void subscribeToTopic() {
        Thread thread = new Thread(() -> {
            try {
                consumerChannel.exchangeDeclare(Constants.TOPIC_NAME, Constants.EXCHANGE_TYPE_FANOUT);

                String queueName = consumerChannel.queueDeclare().getQueue();

                consumerChannel.queueBind(queueName, Constants.TOPIC_NAME, "");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String receivedMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println("Topic received!");
                    System.out.println(receivedMessage);
                };

                consumerChannel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
            } catch (IOException e) {
                e.printStackTrace();
            }

            while(true){}
        });

        thread.start();
    }

    public void closeConnection() throws IOException, TimeoutException {
        consumerChannel.close();
        consumerConnection.close();
    }

}