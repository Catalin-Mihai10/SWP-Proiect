package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    List<Producer> producers = new ArrayList<Producer>();
    List<Consumer> consumers = new ArrayList<Consumer>();
    UserManager userManager = new UserManager();
    ClientModule clientModule = new ClientModule();
    MessageHandler messageHandler = new MessageHandler();

    public Server(){

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

    public void verifyUsersConnectivity(){
        List<String> users = new ArrayList<String>();
        users.addAll(userManager.getUsers());
        boolean isActive = false;
        for(String user: users){
            isActive = clientModule.ping(user);
            if(isActive == false){
                userManager.removeUser(user);
            }
        }
    }

    public static void sendMessage(String message, String user){
        messageHandler.sendMessage(message, user);
    }

    public static void addUser(String user){
        userManager.addUser(user);
    }
}
