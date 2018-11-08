package game.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;

public class MySQLConnection extends AbstractVerticle {

    private static final String URL = "jdbc:mysql://localhost/shop?useSSL=false&serverTimezone=UTC";
    // TODO: naam van database specifiëren
    private static final String user = "Leo"; // TODO: gebruiker hier specifiëren
    private static final String pwd = "leonard"; // TODO: paswoord hier invullen

    private final JsonObject config = new JsonObject()
            .put("url", URL)
            .put("user", user)
            .put("password", pwd);

    private SQLClient client = JDBCClient.createNonShared(vertx, config);

    public SQLClient getClient() {
        return client;
    }
}

