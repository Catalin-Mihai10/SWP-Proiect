class MessageHandler implements IMessageHandler{
    private String message = "";
    private String user = "";

    public MessageHandler() {}

    public String getMessage(){
        return message;
    }

    //TODO: create a return type of the message. May be String or defined enum.
    private void HandleMessageType() {}

    private bool verifyQueue() {
        return true;
    }
}