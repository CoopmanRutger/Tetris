package game.data;

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
        Instant timestamp = Instant.now();
        jdbcClient.updateWithParams(
                "INSERT INTO messages(timestamp, user, message) VALUES (?, ?, ?)",
                new JsonArray().add(timestamp)
                        .add(((JsonObject) message.body()).getString("user"))
                        .add(((JsonObject) message.body()).getString("content")),
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
