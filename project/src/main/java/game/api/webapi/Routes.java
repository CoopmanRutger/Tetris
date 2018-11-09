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

    public void passFaction() {
        eb.consumer("tetris.game.faction", message -> {
            String m = message.body().toString();
            message.reply(m);
            sendBlockOneByOne(game);
        });
    }
// TODO: get faction from DB and change here to DB class

    public void sendBlockOneByOne(Game game) {
        eb.send("tetris.game.test", Json.encode(game));

        System.out.println(game.getPlayers().get(0));
    }
}
