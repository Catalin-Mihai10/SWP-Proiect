package ClientModule;

class clientModule implements clientModuleInterface{
    private String user;

    public String getUser(){
        return this.user;
    }

    public clientModule(String user_){
        this.user = user_;
        addUser(user_);
    }

    @Override
    public void sendMessage(String message_, String user_){
        Server.sendMessage(message_, user_);
    }

    @Override
    private void addUser(String user_){
        Server.addUser(user_);
    }

    @Override
    public boolean ping(){
        return true;
    }

}