package ClientModule;

class clientModule implements clientModuleInterface{
    private String user;
    private String message = "";

    public String getUser(){
        return this.user;
    }

    public String getMessage(){
        return this.message;
    }

    public clientModule(String user_){
        this.user = user_;
    }

    @Override
    public void sendMessage(String message_, String user_){

    }

    @Override
    public String addUser(String user_){

        return null;
    }

    @Override
    public String ping(String user_){

        return null;
    }

}