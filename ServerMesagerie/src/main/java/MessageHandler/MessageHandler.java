class MessageHandler implements IMessageHandler{
    private String message = "";
    private String user = "";

    public MessageHandler() {}

    @Override
    public String sendMessage(String message, String user){
        return message;
    }

    @Override
    public String getMessage(){
        return message;
    }

    //TODO: create a return type of the message. May be String or defined enum.
    private void HandleMessageType() {}

    private boolean verifyQueue() {
        return true;
    }
}