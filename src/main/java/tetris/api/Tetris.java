package tetris.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import tetris.api.jdbcinteractor.ConnectionDatabase;
import tetris.api.jdbcinteractor.Database;
import tetris.webapi.GameController;
import tetris.webapi.Routes;
import tetris.webapi.WebAPI;

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
