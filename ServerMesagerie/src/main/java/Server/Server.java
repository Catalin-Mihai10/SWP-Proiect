package Server;

import java.util.*;
import java.util.function.Consumer;
import java.util.concurrent.*;

public final class Server {

    private static Server server_instance = null;

    private static UserManager userManager = new UserManager();
    private static Map<Integer, ClientModule> clients = new ConcurrentHashMap<Integer, ClientModule>();
    private static Integer id = 0;

    private Server(){

    }

    public static Server getInstance(){
        if(server_instance == null)
            server_instance = new Server();
        return server_instance;
    }

    public void run(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                verifyUsersConnectivity();
            }
        };
        Timer timer  = new Timer();
        while(true){
            timer.schedule(task, 500);
        }
    }

    private void verifyUsersConnectivity(){
        List<String> users = new ArrayList<String>();
        users.addAll(userManager.getUsers());
        boolean isActive = false;
        for(String user: users){
            Iterator<Map.Entry<Integer, ClientModule>> iterator = clients.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, ClientModule> entry = iterator.next();
                if(user.equals(entry.getValue())){
                    try {
                        entry.getValue().ping();
                    } catch (NullPointerException e) {
                        boolean wasRemoved = false;
                        wasRemoved = userManager.removeUser(user);
                        if(wasRemoved)iterator.remove();
                    }
                }
            }
        }
    }

    public static void sendMessage(String message, String user, ClientModule client){
        client.getProducer().sendMessage(message, user);
    }

    public static void addUser(ClientModule client){
        boolean wasAdded = false;
        wasAdded = userManager.addUser(client.getUser());
        if(wasAdded) {
            clients.put(id,client);
            id++;
            createProducer(client);
            createConsumer(client);
        }
    }

    private static void createProducer(ClientModule client){
        Producer producer = new Producer(client.getUser());
        client.setProducer(producer);
    }

    private static void createConsumer(ClientModule client){
        Consumer consumer = new Consumer(client.getUser());
        client.setConsumer(consumer);
    }
}
