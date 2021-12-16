package Server;

import Utilitati.Constants;
import UserManager.UserManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import Utilitati.OurTimestamp;
import com.rabbitmq.client.*;
import org.json.simple.parser.ParseException;

import java.util.concurrent.*;

public final class Server extends Thread{

    private static Server server_instance;
    private static UserManager userManager = new UserManager();
    private static Map<String, Long> clients = new ConcurrentHashMap<>();
    private final ScheduledExecutorService serverScheduler = Executors.newScheduledThreadPool(1);
    private static OurTimestamp serverTimestamp = new OurTimestamp();

    private Server(){}

    public static Server getInstance(){
        if(server_instance == null)
            server_instance = new Server();
        return server_instance;
    }

    public void run(){

        final Thread thread = new Thread(() -> {
            try {
                verifyUsersConnectivity();
            } catch (IOException | ParseException e) {}
        });

        serverScheduler.scheduleAtFixedRate(thread, Constants.ZERO_SECONDS, Constants.ONE_SECOND, TimeUnit.MILLISECONDS);

    }

    private void verifyUsersConnectivity() throws IOException, ParseException {
        List<String> users = new ArrayList<>();
        users.addAll(userManager.getUsers());

        for(String user: users){
            Iterator<Map.Entry<String, Long>> iterator = clients.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Long> entry = iterator.next();
                if(user.equals(entry.getKey())) {
                    if( serverTimestamp.getMillis() - entry.getValue() >= Constants.ONE_SECOND){
                        boolean wasRemoved;
                        wasRemoved = userManager.removeUser(user);
                        if (wasRemoved) iterator.remove();
                    }
                }
            }
        }
    }

    public static boolean addUser(String user) throws IOException, TimeoutException, ParseException {
        boolean wasAdded;

        wasAdded = userManager.addUser(user);

        if(wasAdded) {
            OurTimestamp timestamp = new OurTimestamp();
            clients.put(user, timestamp.getMillis());
            return true;
        }

        return false;
    }

    private static boolean isUserConnected(String user){
        Iterator<Map.Entry<String, Long>> iterator = clients.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = iterator.next();
            if(user.equals(entry.getKey())) return true;
        }

        return false;
    }

    public static void setTimestampToUSer(String timestamp, String user){
        Iterator<Map.Entry<String, Long>> iterator = clients.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = iterator.next();
            if(user.equals(entry.getKey())){
                entry.setValue(Long.parseLong(timestamp));
            }
        }
    }

    public static void task() throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.LOCAL_HOST);

        Connection serverConnection = connectionFactory.newConnection();
        Channel serverChannel = serverConnection.createChannel();
        serverChannel.queueDeclare(Constants.SERVER_NAME, false, false, false, null);
        serverChannel.queuePurge(Constants.SERVER_NAME);

        serverChannel.basicQos(1);

        DeliverCallback serverDeliverCallback = (consumer, delivery) -> {

            AMQP.BasicProperties replyProprieties = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            String response = "";

            response = getResponse(delivery, response);

            serverChannel.basicPublish("", delivery.getProperties().getReplyTo(), replyProprieties, response.getBytes(StandardCharsets.UTF_8));
            serverChannel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        serverChannel.basicConsume(Constants.SERVER_NAME,  false, serverDeliverCallback, (consumer -> {}));

        while(true){}
    }

    private static String getResponse(Delivery delivery, String response) throws IOException {
        String[] commandAndClient = new String(delivery.getBody(), StandardCharsets.UTF_8).split(Constants.COLON_SEPARATOR);

        switch(commandAndClient[0]){
            case Constants.ADD_USER_COMMAND:
                try {
                    if(addUser(commandAndClient[1])) response = Constants.TRUE;
                    else response = Constants.FALSE;
                } catch (TimeoutException | ParseException e) {
                    e.printStackTrace();
                }
                break;
            case Constants.PING:
                setTimestampToUSer(commandAndClient[1], commandAndClient[2]);
                break;
            case Constants.CHECK_USER_STATUS_COMMAND:
                if(isUserConnected(commandAndClient[1])) response = Constants.TRUE;
                else response = Constants.FALSE;
                break;
            default:
        }

        return response;
    }
}