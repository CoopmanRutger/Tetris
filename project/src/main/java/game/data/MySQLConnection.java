//package game.data;
//
//import io.vertx.core.Vertx;
//import io.vertx.core.json.JsonObject;
//import io.vertx.ext.jdbc.JDBCClient;
//import io.vertx.ext.sql.SQLClient;
//
//public class MySQLConnection {
//
//    private static final String URL = "jdbc:mysql://localhost/database?useSSL=false&serverTimezone=UTC";
//    // TODO: naam van database specifiëren
//    private static final String user = "User"; // TODO: gebruiker hier specifiëren
//    private static final String pwd = "pwd"; // TODO: paswoord hier invullen
//
//    private JsonObject connection = new JsonObject()
//            .put("url", URL)
//            .put("user", user)
//            .put("password", pwd);
//
//    private SQLClient client = JDBCClient.createShared(Vertx.vertx(), connection);
//
//    public SQLClient getClient() {
//        return client;
//    }
//}
