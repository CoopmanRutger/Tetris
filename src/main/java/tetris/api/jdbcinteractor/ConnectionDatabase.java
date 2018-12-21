package tetris.api.jdbcinteractor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import org.h2.tools.Server;
import org.pmw.tinylog.Logger;
import tetris.webapi.GameController;

import java.sql.SQLException;

/**
 * @author Remote Access Tetris aka RAT
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
        return new ConsumerHandlers(controller);
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
                conn.query("drop table if exists abilities;"
                        + "drop table if exists blocks;"
                        + "drop table if exists clans;"
                        + "drop table if exists events;"
                        + "drop table if exists factions;"
                        + "drop table if exists factions_users;"
                        + "drop table if exists heroes;"
                        + "drop table if exists heroes_abilities;"
                        + "drop table if exists players;"
                        + "drop table if exists users;"
                        + "create table if not exists factions (factionNr int not null primary key auto_increment "
                        + ",name varchar(40))engine=InnoDB;"
                        + "create table if not exists heroes(heroNr int not null primary key "
                        + "auto_increment ,name varchar(40))engine=InnoDB;"

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
                        + ", constraint fk_heroNr4 foreign key (heroNr) references heroes(heroNr))engine=InnoDB;"

                        + "insert into heroes(name)\n"
                        + "values ('Matthias de boer'), ('Ann de boerin'), ('Jillke de ridder'), "
                        + "('Christian de ridder'), ('Heidi de kokin'), ('Frederick de kok'), ('x de jokster'),"
                        + " ('x de joker'), ('x de wizard'), ('x de wizard'), ('empty');"

                        + "insert into abilities(name, startvalue, level)"
                        + "values ('Hay Bale', 750, 1), ('Pitchfork', 500, 1), ('Slash', 500, 1), "
                        + "('Big Swing', 500, 1), ('Steal Ingredients', 500, 1), ('Fury Cooking', 1000, 1), "
                        + "('Cheering Crowd', 750, 1), ('Joker', 1000, 1),('Slow', 500, 1), ('Freeze', 750, 1);"

                        + "insert into users ( username, email, password, gold) "
                        + "values ('Rutger', 'rc@abc.be', "
                        + "'$2a$10$dwHsENkVpDMf0rCuX/QViOutTCWRqI7BKRaICRTdHWQeeQL6G2E2S', 1150),"
                        + "('Louis', 'Lo@abc.be', '$2a$10$dwHsENkVpDMf0rCuX/QViOutTCWRqI7BKRaICRTdHWQeeQL6G2E2S', 380),"
                        + "('Jef', 'jef@abc.be', '$2a$10$dwHsENkVpDMf0rCuX/QViOutTCWRqI7BKRaICRTdHWQeeQL6G2E2S', 0),"
                        + "('Jos', 'jos@abc.be', '$2a$10$dwHsENkVpDMf0rCuX/QViOutTCWRqI7BKRaICRTdHWQeeQL6G2E2S', 10),"
                        + "('Jus', 'jus@abc.be', '$2a$10$dwHsENkVpDMf0rCuX/QViOutTCWRqI7BKRaICRTdHWQeeQL6G2E2S', 250);"

                        + "insert into factions (name) "
                        + "values ('dark green'), ('dark blue'), ('dark red'), ('dark yellow'), ('dark');"

                        + "insert into Clans( clannr, name, factionnr ) "
                        + "values (1, 'Dragons', 3), (2, 'Masters', 3), (3, 'Kings', 3),"
                        + "(4, 'Queens', 3), (5, 'Ultra', 3);"

                        + "insert into factions_users ( factionnr, userid) "
                        + "values (1, 1), (2, 2), (2, 3), (3, 4), (4, 5);"

                        + "insert into players (userid, playername, heronr, xp, level ) "
                        + "values (1, 'Rutger123', 1, 950, 2), (2, 'RipperLouis', 3, 250, 1);",
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

