package game.api;

import game.api.jdbcinteractor.ConnectionDatabase;
import game.api.jdbcinteractor.ConsumerHandlers;
import game.api.jdbcinteractor.Database;
import game.api.webapi.Routes;
import game.api.webapi.WebAPI;
import game.api.webapi.GameController;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;


public class Tetris extends AbstractVerticle {

    @Override
    public void start() {
        ConnectionDatabase connectionDatabase = new ConnectionDatabase();
        Database.setDB((ConsumerHandlers) connectionDatabase.getConsumerHandler());
        GameController gameController = new GameController();



        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(gameController);
        vertx.deployVerticle(new WebAPI(gameController));
        vertx.deployVerticle(connectionDatabase);
        vertx.deployVerticle(new Routes(gameController));


    }
}