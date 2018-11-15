package game.api.jdbcinteractor;

import game.api.webapi.Routes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import org.h2.tools.Server;
import org.pmw.tinylog.Logger;

import java.sql.SQLException;


public class ConnectionDatababase extends AbstractVerticle {

    private Server dbServer;
    private Server webDB;
    private JDBCClient jdbcClient;


    private void initializeDB() {
    jdbcClient = JDBCClient.createNonShared(vertx, new JsonObject()
            .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
            .put("driverClassName", "org.h2.Driver")
            .put("jdbcUrl", "jdbc:h2:~/tetrisDatabank")
            .put("username", "sa")
            .put("password", ""));
    jdbcClient.getConnection(res -> {
        if (res.succeeded()) {
            final SQLConnection conn = res.result();
            conn.query("CREATE TABLE IF NOT EXISTS "
                            + "game(id INT PRIMARY KEY auto_increment, "
                            + "user1 VARCHAR(255),user2 VARCHAR(255), events VARCHAR(255))",
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


        // consumers( routes
        eb.consumer("tetris.infoBackend.homescreen", consumerHandlers::storeMessage);

//        Routes routes = new Routes();
//        routes.homeScreen();

    }





    @Override
    public void stop(){
        dbServer.stop();
        webDB.stop();
    }






}

