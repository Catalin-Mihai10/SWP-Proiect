package Initializer;

import ClientModule.ClientModule;
import Exceptions.UserAlreadyExistsException;
import Exceptions.UserIsOfflineException;
import Server.Server;
import Utilitati.Constants;
import Utilitati.ConstructMessage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Initializer {

    public static void initialize() throws IOException, TimeoutException {
        AtomicInteger count= new AtomicInteger(0);

        System.out.println("Scrie server pentru a deschide un server sau client pentru a intra in aplicatie");
        Scanner scanner = new Scanner(System.in);
        switch(scanner.nextLine()){
            case Constants.SERVER_NAME:
                initializeServer();
                break;
            case Constants.CLIENT:
                initializeClient(count, scanner);
                break;
            default:
        }
    }

    private static void initializeServer() throws IOException, TimeoutException {
        Server serverInstance = Server.getInstance();
        serverInstance.start();
        serverInstance.task();
    }

    private static void initializeClient(AtomicInteger count, Scanner scanner) {
        System.out.println("Dati nume pentru a intra in aplicatie");
        try {
            ClientModule newClient = null;

            while(count.get() < 3){
                try {
                    newClient = new ClientModule(scanner.nextLine());
                    break;
                }catch(UserAlreadyExistsException e){
                    e.getMessage();
                    count.incrementAndGet();
                    System.out.println("Incercati din nou!");
                } catch (UserIsOfflineException e) {
                    e.getMessage();
                }
            }

            if(count.get() == 3) System.exit(0);

            System.out.println("Meniu\nScrieti destinatar:mesaj pentru a trimite un mesaj\nScrieti exit pentru a iesi: ");
            String input;
            while(!(input = scanner.nextLine()).isEmpty()){
                switch(input){
                    case Constants.EXIT:
                        newClient.getProducer().closeConnection();
                        newClient.getConsumer().closeConnection();
                        System.exit(0);
                    default:
                        if(input.contains(Constants.COLON_SEPARATOR)) {
                            String[] numeSiMesaj = input.split(Constants.COLON_SEPARATOR);
                            ConstructMessage constructMessage = new ConstructMessage(newClient.getUser(), numeSiMesaj[0], numeSiMesaj[1], "");
                            newClient.sendMessage(constructMessage);
                        }
                }
            }
        } catch (IOException | TimeoutException | ParseException e) {
            e.printStackTrace();
        }
    }

}
