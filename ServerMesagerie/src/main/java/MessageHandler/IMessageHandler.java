package MessageHandler;

interface IMessageHandler {

    public void sendMessage(String message, String user);
    public void postTopic();
    public String getMessage();
}