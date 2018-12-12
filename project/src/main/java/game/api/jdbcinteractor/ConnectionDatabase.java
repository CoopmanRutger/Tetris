package game.api.jdbcinteractor;

import game.api.webapi.GameController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import org.h2.tools.Server;
import org.pmw.tinylog.Logger;

import java.sql.SQLException;

/**
 * @author      Remote Access Tetris aka RAT
 */
public class ConnectionDatabase extends AbstractVerticle {

    private static JDBCClient jdbcClient;
    private Server dbServer;
    private Server webDB;
    private ConsumerHandlers consumerHandlers;

    public static JDBCClient getJdbcClient() {
        return jdbcClient;
    }

    public static void setJdbcClient(JDBCClient jdbcClient) {
        ConnectionDatabase.jdbcClient = jdbcClient;
    }

    public ConsumerHandlers getConsumerHandlers(GameController controller) {
        final ConsumerHandlers consumerHandlers = new ConsumerHandlers(controller);
        return consumerHandlers;
    }


    private void initializeDB() {
        jdbcClient = JDBCClient.createNonShared(vertx, new JsonObject()
            .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
            .put("driverClassName", "org.h2.Driver")
            .put("jdbcUrl", "jdbc:h2:~/tetris-21")
            .put("username", "sa")
            .put("password", ""));
        jdbcClient.getConnection(res -> {
            if (res.succeeded()) {
                final SQLConnection conn = res.result();
                conn.query("create table if not exists factions (factionNr int not null primary key auto_increment "
                                + ",name varchar(40))engine=InnoDB;"
                                + "create table if not exists heroes(heroNr int not null primary key auto_increment "
                            + ",name varchar(40))engine=InnoDB;"

                            + "create table if not exists events (eventNr int not null primary key auto_increment "
                            + ",name varchar(40),eventTrigger varchar(20))engine=InnoDB;"

                            + "create table if not exists blocks (blockNr int not null primary key auto_increment "
                            + ",typeOfBlock varchar(50))engine=InnoDB;"

                            + "create table if not exists users (userId int not null primary key auto_increment "
                            + ",userName varchar(40), email varchar(100), password varchar(200), gold int not null )"
                            + " engine=InnoDB;"

                            + "create table if not exists abilities (abilityNr int not null primary key auto_increment "
                            + ",name varchar(40),startValue int not null,level int)engine=InnoDB;"

                            + "create table if not exists clans (clanNr int not null primary key,name varchar(50) "
                            + ",factionNr int not null,constraint fk_factionNr4 foreign key (factionNr) references"
                            + " factions(factionNr))engine=InnoDB;"

                            + "create table if not exists heroes_abilities (heroNr int not null,abilityNr int not null "
                            + ",constraint pk_heroesAbilities primary key (heroNr, abilityNr) ,constraint fk_heroNr1"
                            + " foreign key (heroNr) references heroes(heroNr),constraint fk_abilityNr1 foreign key"
                            + " (abilityNr) references abilities(abilityNr))engine=InnoDB;"

                            + "create table if not exists factions_users (factionNr int not null, userId int not null "
                            + ",constraint pk_factionsUsers primary key (factionNr, userId) ,constraint fk_userId3"
                            + " foreign key (userId) references users(userId),constraint fk_factionNr5 foreign key"
                            + " (factionNr) references factions(factionNr))engine=InnoDB;"

                            + "create table if not exists players (userId int not null,playerName varchar(40) "
                            + ",heroNr int,xp int not null,level int not null,constraint pk_players primary key"
                            + " (userId),constraint fk_userId5 foreign key (userId) references users(userId)"
                            + ", constraint fk_heroNr4 foreign key (heroNr) references heroes(heroNr))engine=InnoDB;" ,
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
            webDB = Server.createWebServer("-webPort", "9021").start();
        } catch (SQLException e) {
            Logger.warn("Error starting the database: {}", e.getLocalizedMessage());
            Logger.debug(e.getStackTrace());
        }
    }

    @Override
    public void start() {
        startServer();
        initializeDB();
        final EventBus eb = vertx.eventBus();
    }

    @Override
    public void stop() {
        dbServer.stop();
        webDB.stop();
    }


    public ConsumerHandlers getConsumerHandler() {
        return consumerHandlers;
    }

    public void setConsumerHandler(ConsumerHandlers consumerHandler) {
        this.consumerHandlers = consumerHandler;
    }
}

