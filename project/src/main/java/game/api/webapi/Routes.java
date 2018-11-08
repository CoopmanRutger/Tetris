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

    public void BattleFieldStart(){
        eb.consumer("tetris.game.BattleField",message -> {
            message.reply(message.body());
        });
    }

    public void test(){
        eb.send("tetris.game.test", "test");
    }
}
