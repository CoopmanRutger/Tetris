package game.api.webapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import org.pmw.tinylog.Logger;


public class WebAPI extends AbstractVerticle {

    private GameController controller;

    public WebAPI(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
        Logger.info("webApi");
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        Routes routes = new Routes(controller);
        router.route("/").handler(routes::rootHandler);
        router.route("/static/*").handler(StaticHandler.create());
        router.route("/tetris-21/socket/*").handler(new TetrisSockJSHandler(vertx).create());
        server.requestHandler(router::accept).listen(8021);
        //        router.route("/tetris/game/*").handler(new TetrisSockJSHandler(vertx).create());
        //
        //        router.route("/tetris/infoBackend/block").handler(Routes::homeScreen);
        //        router.route("/tetris/game/block").handler(Routes::homeScreen);

    }
}
