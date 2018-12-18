package game.api.webapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.Router;
import org.pmw.tinylog.Logger;

/**
 * @author Remote Access Tetris aka RAT
 */

public class WebAPI extends AbstractVerticle {

    private final GameController controller;

    public WebAPI(final GameController controller) {
        this.controller = controller;
    }

    @Override
    public void start() {
        //System.out.println("webApi");
        Logger.info("webApi");
        final HttpServer server = vertx.createHttpServer();
        final Router router = Router.router(vertx);

        final Routes routes = new Routes(controller);
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
