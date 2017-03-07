package sample;
import java.util.ArrayList;

/**
 * Created by ellenbakksjo on 07.03.2017.
 */

public class Database {

    private static DatabaseConnector connection;

    public static void insert(String query) {
        Database.init();
        connection.insert(query);
        Database.exit();
    }

    public static ArrayList<String> select(String query) {
        Database.init();
        ArrayList<String> result = connection.select(query);
        Database.exit();
        return result;
    }

    private static void init(){
        connection = new DatabaseConnector();
        connection.establishConnection();
    }


    private static  void exit(){
        connection.terminateConnection();
    }

}
