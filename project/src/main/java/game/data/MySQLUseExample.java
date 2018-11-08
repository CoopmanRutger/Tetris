package game.data;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

import java.util.ArrayList;
import java.util.List;

public class MySQLUseExample {

    private MySQLConnection connection;
    private static final String GET_PRODUCTS = "select * from product";

    public MySQLUseExample() {
        connection = new MySQLConnection();
    }

    public void getUsers() {
        connection.getClient().getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection2 = res.result();
                connection2.query("SELECT * FROM users", res2 -> {
                    if (res2.succeeded()) {
                        ResultSet rs = res2.result();
                    } else {
                        throw new IllegalArgumentException("Cannot get this info from database.");
                    }
                });

            } else {
                throw new IllegalStateException("Cannot connect to database.");
            }
        });
        // Meer info over queries op: https://vertx.io/docs/vertx-sql-common/java/
    }

    public List<String> getProducts() {
        List<String> products = new ArrayList<>();
        connection.getClient().getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection1 = res.result();
                connection1.query(GET_PRODUCTS, res2 -> {
                    if (res2.succeeded()) {
                        ResultSet rs = res2.result();
                        List<JsonObject> rows = rs.getRows();
                        for (JsonObject row : rows) {
                            String name = row.getString("name");
                            products.add(name);
                        }
                    } else {
                        throw new IllegalArgumentException("Cannot get this information.");
                    }
                });
            } else {
                throw new IllegalArgumentException("Cannot connect to database.");
            }
        });
        return products;
    }


}


