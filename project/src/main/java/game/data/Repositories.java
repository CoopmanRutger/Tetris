package game.data;

public class Repositories {

    private static Repositories instance = new Repositories();

    public static Repositories getInstance() {
        return instance;
    }

    public MySQLUseExample mySQLUseExample() {
        return new MySQLUseExample();
    }
}