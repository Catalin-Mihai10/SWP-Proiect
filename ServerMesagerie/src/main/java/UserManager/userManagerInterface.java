package userManager;

interface userManagerInterface{
    boolean isConnected(String user_);
    String removeUser(String user_);
    String ping(String user_);
}