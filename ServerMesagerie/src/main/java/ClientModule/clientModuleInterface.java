package ClientModule;

import Exceptions.UserIsOfflineException;
import Producer.Producer;
import Consumer.Consumer;
import Utilitati.ConstructMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface IClientModule {
    String sendMessage(ConstructMessage constructMessage) throws IOException, TimeoutException, UserIsOfflineException;
    Producer getProducer();
    Consumer getConsumer();
    void ping();
}
