package game.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class MySQLConnection extends AbstractVerticle {

    private static final String URL = "jdbc:mysql://localhost/tetrisGroep21?useSSL=false&serverTimezone=UTC";
    private static final String user = "tetrisUser";
    private static final String pwd = "tetrisGroep21";

    private final JsonObject config = new JsonObject()
            .put("url", URL)
            .put("user", user)
            .put("password", pwd);

    private SQLClient client = JDBCClient.createNonShared(vertx, config);

    public SQLClient getClient() {
        return client;
    }
}

