package game.api.jdbcinteractor;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import org.pmw.tinylog.Logger;

import java.time.Instant;

public class ConsumerHandlers {

    private JDBCClient jdbcClient;

    public ConsumerHandlers(JDBCClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    public <T> void storeMessage(final Message message) {
        Instant name = Instant.now();
        jdbcClient.updateWithParams(
                "INSERT INTO heroes(name) VALUES (?)",
                new JsonArray().add(((JsonObject) message.body()).getString("name")),
//                        .add(((JsonObject) message.body()).getString("user"))
//                        .add(((JsonObject) message.body()).getString("content")),
                res -> {
                    if (res.succeeded()) {
                        res.result();
                        Logger.info("inserted: {} ", res.result().toJson());
                    } else {
                        Logger.warn("Could not write message to db: {} ", res.cause());
                    }
                });
    }
}
