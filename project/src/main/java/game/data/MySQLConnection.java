package game.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import org.h2.tools.Server;
import org.pmw.tinylog.Logger;

import java.sql.SQLException;


public class MySQLConnection extends AbstractVerticle {

    private Server dbServer;
    private Server webDB;
    private JDBCClient jdbcClient;


private void initializeDB() {
    jdbcClient = JDBCClient.createNonShared(vertx, new JsonObject()
            .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
            // gebruik van H2 DB, deze driver is hiervoor nodig
            .put("driverClassName", "com.mysql.jdbc.Driver")
//            .put("driverClassName", "org.h2.Driver")            // db wordt opgeslagen in chatty in je eigen root
            .put("jdbcUrl", "jdbc:mysql://localhost/tetrisGroep21?useSSL=false&serverTimezone=UTC")
            // standaard user
            .put("username", "tetrisUser")
            // standaard paswoord
            .put("password", "tetrisGroep21"));
    jdbcClient.getConnection(res -> {
        if (res.succeeded()) {
            final SQLConnection conn = res.result();
            conn.query("CREATE TABLE IF NOT EXISTS "
                            + "messages(id INT PRIMARY KEY auto_increment, "
                            + "timestamp TIMESTAMP, user VARCHAR(255), message VARCHAR(255))",
                    queryResult -> {
                        if (queryResult.succeeded()) {
                            Logger.info("Database started");
                        } else {
                            Logger.warn("Database error!");
                        }
                    });
        }
    });
}


    private void startServer() {
        try {
            dbServer = Server.createTcpServer().start();
            webDB = Server.createWebServer().start();
        } catch (SQLException e) {
            Logger.warn("Error starting the database: {}", e.getLocalizedMessage());
            Logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void start() {
        startServer();
        initializeDB();
        EventBus eb = vertx.eventBus();
        ConsumerHandlers consumerHandlers = new ConsumerHandlers(jdbcClient);
        eb.consumer("chatty.store.message", consumerHandlers::storeMessage);
    }

    @Override
    public void stop(){
        dbServer.stop();
        webDB.stop();
    }
}

