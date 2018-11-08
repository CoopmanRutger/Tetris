package game.api.webapi;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class Routes {
    private EventBus eb;

    public Routes() {
        Vertx vertx = Vertx.vertx();
        eb = vertx.eventBus();
        vertx.deployVerticle(new WebAPI());
    }

    public void homeScreen(){
        eb.consumer("tetris.game.homescreen",message -> {
            message.reply(message.body());
        });
    }

    public void route2(){
        eb.consumer("tetris.game.play",message -> {
            message.reply(message.body());
        });
    }
}
