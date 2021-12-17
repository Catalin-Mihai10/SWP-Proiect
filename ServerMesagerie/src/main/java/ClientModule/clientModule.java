package ClientModule;

import Utilitati.Constants;
import Consumer.Consumer;
import Exceptions.UserIsOfflineException;
import Producer.Producer;
import Utilitati.ConstructMessage;
import Utilitati.OurTimestamp;
import org.json.simple.parser.ParseException;

import Exceptions.UserAlreadyExistsException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClientModule implements IClientModule{
    private Producer producer;
    private Consumer consumer;
    private String user;
    private final ScheduledExecutorService clientScheduler = Executors.newScheduledThreadPool(1);
    private OurTimestamp timestamp;

    public ClientModule(String user_) throws IOException, TimeoutException, ParseException, UserAlreadyExistsException, UserIsOfflineException {
        user = user_;
        producer = new Producer();
        consumer = new Consumer(user);

        ConstructMessage constructMessage = new ConstructMessage(user, Constants.SERVER_NAME, user, Constants.ADD_USER_COMMAND);
        String result = sendMessage(constructMessage);
        if(result.equals(Constants.FALSE)) throw new UserAlreadyExistsException(user);

        timestamp = new OurTimestamp();
        ping();
        consumer.getMessage();
        consumer.subscribeToTopic();
    }

    public String getUser(){ return user; }

    public Producer getProducer() { return producer; }

    public Consumer getConsumer() {
        return consumer;
    }

    @Override
    public String sendMessage(ConstructMessage constructMessage) throws IOException, TimeoutException {
        try {
            String result = producer.sendMessage(constructMessage);
            if (result.equals(Constants.NOT_FOUND)) throw new UserIsOfflineException();

        }catch ( UserIsOfflineException e){
            String message = e.getMessage();
            System.out.println(message);
        }finally {
            return "";
        }
    }

    @Override
    public void ping(){

        final Runnable runnable = () -> {
            try {
                ConstructMessage constructMessage = new ConstructMessage(user, Constants.SERVER_NAME, timestamp.getMillis().toString(), Constants.PING);
                sendMessage(constructMessage);
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        };

        clientScheduler.scheduleAtFixedRate(runnable, Constants.ZERO_SECONDS, Constants.ONE_SECOND/2, TimeUnit.MILLISECONDS);
    }

}
