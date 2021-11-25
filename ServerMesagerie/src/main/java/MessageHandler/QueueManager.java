package MessageHandler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

class QueueManager {
    private Queue<String> messageQueue;
    private int messagesCounter;
    private final int MAX_NUMBER_OF_MESSAGES = 1000;

    public QueueManager(){
        this.messagesCounter = 0;
        this.messageQueue = new LinkedList<String>();
    }

    public void checkQueue(String message) {
        if(queueIsFull(messagesCounter)){
            removeMessage();
            addMessage(message);
        }
        else addMessage(message);
    }

    private void removeMessage() {
        try{
            messageQueue.remove();
            --messagesCounter;
        }catch(NoSuchElementException e){
            System.out.println("Exception: " + e);
        }
    }

    private void addMessage(String newMessage) {
        try{
            messageQueue.add(newMessage);
            ++messagesCounter;
        }catch(IllegalStateException | ClassCastException | NullPointerException | IllegalArgumentException e){
            System.out.println("Exception: " + e);
        }
    }

    private boolean queueIsFull(int numberOfMessagesInQueue){
        if(numberOfMessagesInQueue == MAX_NUMBER_OF_MESSAGES) return true;
        return false;
    }
}