package Utilitati;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    //Server
    public static final String ADD_USER_COMMAND =  "addUser";
    public static final String RETRIGGER_WATCHDOG_COMMAND = "retrigger";
    public static final String CHECK_USER_STATUS_COMMAND = "isConnected";
    public static final Integer ZERO_SECONDS = 0;
    public static final Integer ONE_SECOND = 1000;
    public static final Integer ONE_MILISECOND = 1;
    public static final String  PING = "ping";

    //MessageHandler/Server/Main
    public static final java.util.UUID UUID = null;
    public static final String LOCAL_HOST = "localhost";
    public static final String TOPIC_EXPIRATION_TIME = "5000";
    public static final String TOPIC_NAME = "topics_queue";
    public static final String SERVER_NAME = "server";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String MESSAGE_TYPE_SERVER = "server";
    public static final String MESSAGE_TYPE_TOPIC = "topic";
    public static final String COLON_SEPARATOR = ":";
    public static final String NOT_FOUND = "not found";
    public static final String EXCHANGE_TYPE_HEADER = "headers";
    public static final String EXCHANGE_TYPE_FANOUT = "fanout";

    //Watchdog
    public static final Integer WATCHDOG_TIMEOUT = 1500;

    //UserManager
    public static final Path DESTINATION_PATH = Paths.get("classes", "userList.json");
    public static final String USER = "user";

    //Main
    public static final String CLIENT = "client";
    public static final String EXIT = "exit";
}
