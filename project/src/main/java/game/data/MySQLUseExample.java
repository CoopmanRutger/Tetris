//package game.data;
//
//import io.vertx.ext.sql.ResultSet;
//import io.vertx.ext.sql.SQLClient;
//import io.vertx.ext.sql.SQLConnection;
//
//public class MySQLUseExample {
//
//    private MySQLConnection connection;
//
//    public MySQLUseExample() {
//        connection = new MySQLConnection();
//
//    }
//
//    public void getUsers() {
//        connection.getClient().getConnection(res -> {
//            if (res.succeeded()) {
//                SQLConnection connection2 = res.result();
//                connection2.query("SELECT * FROM users", res2 -> {
//                    if (res2.succeeded()) {
//                        ResultSet rs = res2.result();
//                    } else {
//                        throw new IllegalArgumentException("Cannot get this info from database.");
//                    }
//                });
//
//            } else {
//                throw new IllegalStateException("Cannot connect to database.");
//            }
//        });
//        // Meer info over queries op: https://vertx.io/docs/vertx-sql-common/java/
//
//    }
//
//
//}
