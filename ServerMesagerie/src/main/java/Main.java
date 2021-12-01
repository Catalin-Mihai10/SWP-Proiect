import Consumer.Consumer;
import Producer.Producer;
import MessageHandler.MessageHandler;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException{
//        ConnectionFactory produceConnection  = new ConnectionFactory();
//        produceConnection.setHost("localhost");
//
//        try(Connection newConnection = produceConnection.newConnection()){
//            Channel newChannel = newConnection.createChannel();
//            newChannel.queueDeclare("consumer", false, false, false, null);
//
//            String message = "Hello Consumer!";
//
//            newChannel.basicPublish("", "consumer", false, null, message.getBytes());
//            System.out.println("message sent sucesfully");
//        }catch(IOException | TimeoutException e){
//            e.printStackTrace();
//        }
//
//        Consumer newConsumer = new Consumer();
//        newConsumer.getConsumerChannel().basicConsume("consumer", true, (consumerTag, message) -> {
//            String m = new String(message.getBody(), "UTF-8");
//            System.out.println("Messaged received: " + m);
//        }, consumerTag -> {});
        MessageHandler messageHandler = new MessageHandler();
        Producer producer = new Producer();
        Consumer consumer =  new Consumer();

        String queueName = "producer-consumer";

        producer.setQueueName(queueName);
        consumer.setQueueName(queueName);

        producer.setMessageHandler(messageHandler);
        consumer.setMessageHandler(messageHandler);

        producer.sendMessage("Salut test!", "test");
        consumer.getMessage();

        producer.closeConnection();
        consumer.closeConnection();
    }
}
