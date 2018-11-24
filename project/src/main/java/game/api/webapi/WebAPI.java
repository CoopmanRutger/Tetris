package game.api.webapi;

import game.Game;
import game.events.Events;
import game.player.Player;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.Router;


public class WebAPI extends AbstractVerticle {

    private Server server1 = new Server();

    @Override
    public void start() {
        System.out.println("webApi");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Routes routes = new Routes();
        router.route("/").handler(routes::rootHandler);
        router.route("/static/*").handler(StaticHandler.create());
        router.route("/tetris/infoBackend/*").handler(new TetrisSockJSHandler(vertx).create());
        server.requestHandler(router::accept).listen(8000);
//        router.route("/tetris/game/*").handler(new TetrisSockJSHandler(vertx).create());
//
//        router.route("/tetris/infoBackend/block").handler(Routes::homeScreen);
//        router.route("/tetris/game/block").handler(Routes::homeScreen);

    }
}