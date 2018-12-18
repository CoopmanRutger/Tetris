package game.api.jdbcinteractor;

/**
 * @author Remote Access Tetris aka RAT
 */

public final class Database {
    private static ConnectionDatabase connectionDatabase;

    private Database() {

    }

    public static ConnectionDatabase getDB() {
        return connectionDatabase;
    }

    public static void setDB(final ConsumerHandlers consumerHandler) {
        connectionDatabase = new ConnectionDatabase();
        connectionDatabase.setConsumerHandler(consumerHandler);
    }
}
