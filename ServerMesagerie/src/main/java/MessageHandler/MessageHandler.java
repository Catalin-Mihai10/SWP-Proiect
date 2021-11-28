package MessageHandler;

class MessageHandler implements IMessageHandler{

    private enum messageTypes {
        TOPIC, PRIVATE, UNDEFINED
    }

    private String message;
    private String user;
    private messageTypes type;
    private QueueManager queueManager;

    public MessageHandler() {
        this.message = "";
        this.user = "";
        this.type = messageTypes.UNDEFINED;
        this.queueManager = new QueueManager();
    }

    @Override
    public void sendMessage(String message, String user){
        this.message = message;
        this.user = user;

        if(this.user == "topic") type = messageTypes.TOPIC;
        type = messageTypes.PRIVATE;
    }

    private void HandleMessageType() {

        switch(type){
            case TOPIC:
                 postTopic();
                 break;
            case PRIVATE:
                 verifyQueue();
                 break;
            default:
                System.out.println("ERROR: UNDEFINED MESSAGE TYPE");
                break;
        }
    }

    //TODO: think about posting Topics
    @Override
    public void postTopic(){
        //Server.getOnlineUsers(); something like this
        //iterate through every user and send message
    }

    private void verifyQueue() {
        queueManager.checkQueue(this.message);
    }

    @Override
    public String getMessage(){
        return message;
    }
}