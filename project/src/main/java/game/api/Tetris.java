package game.api;

import game.api.jdbcinteractor.ConnectionDatabase;
import game.api.webapi.Routes;
import game.api.webapi.WebAPI;
import game.api.webapi.Server;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;


public class Tetris extends AbstractVerticle {

    @Override
    public void start() {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Server());
        vertx.deployVerticle(new WebAPI());
        vertx.deployVerticle(new ConnectionDatabase());
        vertx.deployVerticle(new Routes());
    }
}