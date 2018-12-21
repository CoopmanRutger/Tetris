package game.api;

import game.api.jdbcinteractor.ConnectionDatabase;
import game.api.jdbcinteractor.Database;
import game.api.webapi.GameController;
import game.api.webapi.Routes;
import game.api.webapi.WebAPI;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * @author Remote Access Tetris aka RAT
 */
public class Tetris extends AbstractVerticle {

    @Override
    public void start() {
        final ConnectionDatabase connectionDatabase = new ConnectionDatabase();
        Database.setDB(connectionDatabase.getConsumerHandler());
        final GameController gameController = new GameController();

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(gameController);
        vertx.deployVerticle(new WebAPI(gameController));
        vertx.deployVerticle(connectionDatabase);
        vertx.deployVerticle(new Routes(gameController));
    }
}
