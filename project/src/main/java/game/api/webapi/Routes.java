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


    public Routes() {
        Vertx vertx = Vertx.vertx();
        eb = vertx.eventBus();
        vertx.deployVerticle(new WebAPI());
//        this.game = game;
    }

    public void homeScreen(){
        eb.consumer("tetris.game.homescreen", message -> {
            message.reply(message.body());
        });
    }

    public void BattleFieldStart(){
        eb.consumer("tetris.game.BattleField",message -> {
            String m = message.body().toString();
            message.reply(m);
            sendBlockOneByOne(game);
        });
    }

    public void chooseFaction() {
        eb.consumer("tetris.game.faction.choose", message -> {
            String faction = message.body().toString();
            message.reply(faction);
        });
    }
    // TODO: pass faction to DB.

    public void getFactionInfo() {
        eb.consumer("tetris.game.faction.get", message -> {
            String m = message.body().toString();
            message.reply(null);
        });
    }
    // TODO: get faction from DB.

    public void sendBlockOneByOne(Game game) {
        eb.send("tetris.game.test", Json.encode(game));

        System.out.println(game.getPlayers().get(0));
    }
}
