package game.api.webapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.Router;


public class WebAPI extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            final HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write("Hello yeah");
            response.end();
        });
        server.requestHandler(router::accept).listen(8082);
        router.route("/static/*").handler(StaticHandler.create());
        router.route("/tetris/game/*").handler(new TetrisSockJSHandler(vertx).create());
//        router.route("/tetris/game/block").handler(Routes::homeScreen);

    }
}