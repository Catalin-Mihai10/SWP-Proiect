package ClientModule;

class clientModule implements clientModuleInterface{
    private Producer producer;
    private Consumer consumer;
    private String user;

    public String getUser(){
        return this.user;
    }

    public clientModule(String user_){
        this.user = user_;
        addUser(user_);
    }

    public void setProducer(Producer producer_) {
        this.producer = producer_;
    }

    public Producer getProducer() {
        return this.producer;
    }

    public void setConsumer(Consumer consumer_) {
        this.consumer = consumer_;
    }

    public Consumer getConsumer() {
        return this.consumer;
    }

    @Override
    public void sendMessage(String message_, String user_){
        Server.sendMessage(message_, user_);
    }

    @Override
    private void addUser(){
        Server.addUser(this);
    }

    @Override
    public boolean ping(){
        return true;
    }

}