package game.data;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import org.pmw.tinylog.Logger;
import java.util.ArrayList;
import java.util.List;

//public class MySQLTetris {
//
//    private static final String GET_FACTION = "select * from faction";
//    private MySQLConnection connection;
//
//    public MySQLTetris() {
//        connection = new MySQLConnection();
//    }
//
//    public List<String> getFactions() {
//        List<String> factions = new ArrayList<>();
//        connection.getClient().getConnection(res -> {
//            if (res.succeeded()) {
//                SQLConnection conn = res.result();
//                conn.query(GET_FACTION, queryResult -> {
//                   if (queryResult.succeeded()) {
//                       ResultSet result = queryResult.result();
//                       List<JsonObject> rows = result.getRows();
//                       for (JsonObject row : rows) {
//                           String faction = row.getString("name");
//                           factions.add(faction);
//                       }
//                   } else {
//                       Logger.warn("Couldn't get this information.");
//                   }
//                });
//            } else {
//                throw new IllegalStateException("Cannot connect to database.");
//            }
//        });
//        return factions;
//    }
//}
