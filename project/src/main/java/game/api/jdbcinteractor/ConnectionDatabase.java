package game.api.jdbcinteractor;

import game.api.webapi.GameController;
import game.api.webapi.Routes;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import org.h2.tools.Server;
import org.pmw.tinylog.Logger;

import java.sql.SQLException;


public class ConnectionDatabase extends AbstractVerticle {
    //http://localhost:9021 voor database  naam:/tetris-21

    public static JDBCClient jdbcClient;
    private Server dbServer;
    private Server webDB;
    private ConsumerHandlers consumerHandlers;


    public ConsumerHandlers getConsumerHandlers(GameController controller) {
        ConsumerHandlers consumerHandlers = new ConsumerHandlers(controller);
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
            conn.query( "create table if not exists faction (factionNr int not null primary key auto_increment,name varchar(40))engine=InnoDB;" +
                            "create table if not exists heroes(heroNr int not null primary key auto_increment,name varchar(40))engine=InnoDB;" +
                            "create table if not exists events (eventNr int not null primary key auto_increment,name varchar(40),eventTrigger varchar(20))engine=InnoDB;" +
                            "create table if not exists blocks (blockNr int not null primary key auto_increment,typeOfBlock varchar(50))engine=InnoDB;" +
                            "create table if not exists users (userId int not null primary key auto_increment, userName varchar(40), email varchar(100))engine=InnoDB;" +
                            "create table if not exists abilities (abilityNr int not null primary key auto_increment,name varchar(40),startValue int not null,level int)engine=InnoDB;" +
                            "create table if not exists clans (clanNr int not null primary key,name varchar(50),factionNr int not null,constraint fk_factionNr2 foreign key (factionNr) references faction(factionNr))engine=InnoDB;" +
                            "create table if not exists heroes_abilities (heroNr int not null,abilityNr int not null,constraint pk_heroesAbilities primary key (heroNr, abilityNr), constraint fk_heroNr foreign key (heroNr) references heroes(heroNr),constraint fk_abilityNr foreign key (abilityNr) references abilities(abilityNr))engine=InnoDB;" +
                            "create table if not exists clan_users (userNr int not null,clanNr int not null,constraint pk_clanUsers primary key (userNr, clanNr), constraint fk_userNr foreign key (userNr) references users(userId),constraint fk_clanNr foreign key (clanNr) references clans(clanNr))engine=InnoDB;" +
                            "create table if not exists player (userId int not null,playerName varchar(40),heroNr int,xp int not null,level int not null,constraint pk_player primary key (userId),constraint fk_userId2 foreign key (userId) references users(userId),constraint fk_heroNr2 foreign key (heroNr) references heroes(heroNr))engine=InnoDB;"
                    , queryResult -> {
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
        EventBus eb = vertx.eventBus();
//        consumerHandlers = new ConsumerHandlers(jdbcClient);
//        System.out.println("get faction " + consumerHandlers.getFaction("Rutger123"));
    }

    @Override
    public void stop(){
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

