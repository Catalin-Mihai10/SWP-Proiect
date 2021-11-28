package ClientModule;

interface clientModuleInterface {
    void sendMessage(String message_, String user_);
    void setProducer(Producer producer_);
    Producer getProducer();
    void setConsumer(Consumer consumer_)
    Consumer getConsumer();
    void addUser();
    boolean ping();
}
