package game.api.webapi;

import game.Game;
import game.api.Start;
import game.events.Events;
import game.player.Player;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class Routes {
    private EventBus eb;
    private Game game;


    public Routes(Game game) {
        Vertx vertx = Vertx.vertx();
        eb = vertx.eventBus();
        vertx.deployVerticle(new WebAPI());
        this.game = game;
    }

    public void homeScreen(){
        eb.consumer("tetris.infoBackend.homescreen", message -> {
            message.reply(message.body());
        });
    }

    public void BattleFieldStart(){
        eb.consumer("tetris.infoBackend.BattleField",message -> {
            String m = message.body().toString();
            message.reply(m);
            sendBlockOneByOne(game);
        });
    }



    public void sendBlockOneByOne(Game game) {
        eb.send("tetris.infoBackend.test", Json.encode(game));

        System.out.println(game.getPlayers().get(0));
    }
}
