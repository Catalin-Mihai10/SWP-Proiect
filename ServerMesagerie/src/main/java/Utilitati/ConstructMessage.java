package Utilitati;

import java.io.Serializable;

public class ConstructMessage implements Serializable {
    private String sender = "";
    private String receiver = "";
    private String message  = "";
    private String command = "";

    public ConstructMessage(String sender, String receiver, String message, String command){
        this.sender = sender;
        this.receiver = receiver;
        this. message = message;
        this.command = command;
    }

    public String getSender(){
        return sender;
    }

    public String getReceiver(){return receiver;}

    public String getMessage(){
        return message;
    }

    public String getCommand(){ return command;}

}
