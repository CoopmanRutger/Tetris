package game.api.webapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.Router;


public class WebAPI extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        Routes routes = new Routes();
        router.route("/").handler(routes::rootHandler);
        router.route("/static/*").handler(StaticHandler.create());
        router.route("/tetris/infoBackend/*").handler(new TetrisSockJSHandler(vertx).create());
        server.requestHandler(router::accept).listen(config().getInteger("http.port", 8000));
//        router.route("/tetris/game/*").handler(new TetrisSockJSHandler(vertx).create());

//        router.route("/tetris/infoBackend/block").handler(Routes::homeScreen);
//        router.route("/tetris/game/block").handler(Routes::homeScreen);

    }
}