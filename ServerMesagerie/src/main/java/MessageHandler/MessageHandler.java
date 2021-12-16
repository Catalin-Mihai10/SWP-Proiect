package MessageHandler;

import Utilitati.ConstructMessage;
import Utilitati.Constants;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageHandler implements IMessageHandler{

    public MessageHandler() {}

    @Override
    public String sendMessage(Channel channel, ConstructMessage constructMessage){
        return HandleMessageType(channel, constructMessage);
    }

    private String HandleMessageType(Channel channel, ConstructMessage constructMessage){
        switch(constructMessage.getReceiver()){
            case Constants.MESSAGE_TYPE_TOPIC:
                return postTopic(channel, constructMessage.getMessage(), Constants.MESSAGE_TYPE_TOPIC);
            case Constants.MESSAGE_TYPE_SERVER:
                return sendRequestToServer(channel, constructMessage);
            default:
                if(verifyUserConectivity(channel, constructMessage)) return postMessage(channel, constructMessage);
                return Constants.NOT_FOUND;
        }
    }

    @Override
    public String postTopic(Channel channel, String message, String queueName){

        try {

            channel.exchangeDeclare(Constants.TOPIC_NAME, Constants.EXCHANGE_TYPE_FANOUT);

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .expiration(Constants.TOPIC_EXPIRATION_TIME)
                    .build();

            channel.basicPublish(Constants.TOPIC_NAME , "", properties, message.getBytes());
            return "";
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String postMessage(Channel channel, ConstructMessage constructMessage) {

        try {
            channel.queueDeclare(constructMessage.getReceiver(), false, false, false, null);

            String senderAndMessage = constructMessage.getSender() +
                    Constants.COLON_SEPARATOR +
                    constructMessage.getMessage();

            channel.basicPublish("", constructMessage.getReceiver(), false, null, senderAndMessage.getBytes());
            return "";
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    private String sendRequestToServer(Channel channel, ConstructMessage constructMessage){

        try {

            final String corrId = Constants.UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

            String message = constructMessage.getCommand() +
                    Constants.COLON_SEPARATOR + constructMessage.getMessage() +
                    Constants.COLON_SEPARATOR + constructMessage.getSender();

            channel.basicPublish("", constructMessage.getReceiver(), properties, message.getBytes());

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            }, consumerTag -> {
            });

            String result = response.take();
            channel.basicCancel(ctag);
            return result;
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return "";
    }

    private boolean verifyUserConectivity(Channel channel, ConstructMessage constructMessage){
        try {
            final String corrId = Constants.UUID.randomUUID().toString();
            String replyQueueName = channel.queueDeclare().getQueue();

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build();

            String message = Constants.CHECK_USER_STATUS_COMMAND + Constants.COLON_SEPARATOR + constructMessage.getReceiver();

            channel.basicPublish("", Constants.SERVER_NAME, properties, message.getBytes());

            final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

            String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                    response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            }, consumerTag -> {
            });

            String result = response.take();
            channel.basicCancel(ctag);
            if(result.equals(Constants.TRUE)) return true;
            return false;

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return false;
    }

}