package ClientModule;

interface clientModuleInterface {
    void sendMessage(String message_, String user_);
    String addUser(String user_);
    String ping(String user_);
}
