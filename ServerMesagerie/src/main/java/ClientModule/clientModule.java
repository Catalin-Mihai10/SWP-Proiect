package ClientModule;

class clientModule implements clientModuleInterface{
    private Producer producer;
    private Consumer consumer;
    private String user;
    private final ScheduledExecutorService clientScheduler = Executors.newScheduledThreadPool(1);

    public String getUser(){
        return this.user;
    }

    public clientModule(String user_) throws IOException, TimeoutException, ParseException {
        user = user_;
        producer = new Producer();
        consumer = new Consumer(user);
        consumer.setMessageHandler(producer.getMessageHandler());

        sendMessage(Constants.ADD_USER_COMMAND + ":" + user, Constants.SERVER_NAME);
        ping();
        consumer.getMessage();
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
    public void sendMessage(String message_, String user_) throws IOException, TimeoutException {
        producer.sendMessage(message_, user_);
    }

    @Override
    private void addUser(){
        Server.addUser(this);
    }

    @Override
    public void ping(){
        final Runnable = () -> {
            try {
                sendMessage(Constants.RETRIGGER_WAQTCHDOG_COMMAND + ":" + user, Constants.SERVER_NAME);
            } catch (IOException | TimeoutException e){
                e.printStackTrace();
            }
        };

        clientScheduler.scheduleAtFixedRate(runnable, Constants.ZERO_SECONDS, Constants.ONE_SECOND/2, TimeUnit.MILLISECONDS);
    }

}