package game.api.jdbcinteractor;


public class Database {
    private static ConnectionDatabase connectionDatabase;

    public static ConnectionDatabase getDB(){
        return connectionDatabase;
    }

    public static void setDB(ConsumerHandlers consumerHandler) {
        connectionDatabase = new ConnectionDatabase();
        connectionDatabase.setConsumerHandler(consumerHandler);
    }
}
