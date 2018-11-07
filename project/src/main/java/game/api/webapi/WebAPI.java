package game.api.webapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;


public class WebAPI extends AbstractVerticle {

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route("/").handler(routingContext -> {
            final HttpServerResponse response = routingContext.response();
            response.setChunked(true);
            response.write("Hello chatty");
            response.end();
        });
        server.requestHandler(router::accept).listen(8080);
        router.route("/static/*").handler(StaticHandler.create());
        router.route("/tetris/game/*").handler(new ChattySockJSHandler(vertx).create());
//        router.route("/tetris/game/block").handler(Routes::route1);

    }
}