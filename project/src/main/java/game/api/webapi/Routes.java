package game.api.webapi;

import game.Game;
import game.api.jdbcinteractor.ConnectionDatabase;
import game.api.jdbcinteractor.ConsumerHandlers;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.awt.*;

public class Routes {
    private EventBus eb;
    private Game game;
    private ConnectionDatabase connectionDB;

    void rootHandler(RoutingContext routingContext){
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("hello tetris");
        response.end();

    }

    public Routes(Game game) {
        this.game = game;
        connectionDB.start();
    }

    public void battleFieldStart(){
        System.out.println("yeet");

        eb.consumer("tetris.infoBackend.BattleField",message -> {
            message.reply("ok");
            System.out.println(message);
            sendBlockOneByOne(game);
        });
        eb.send("tetris.infoBackend.BattleField", Json.encode(game) );
    }
//
//    public void battleFieldBlockPositioning() {
//        eb.consumer("tetris.infoBackend.BattleField.positionBlock", message -> {
//            game.getPlayers().get(1).getPlayfields().getPlayfields().get(1)
//            String m = message.body().toString();
//                    .put(OnPlayField(1, 1, new Block("block", TypesOfBlocks.lBlock, Color.RED)));
//            message.reply(m);
//            sendBlockOneByOne(game);
//        });
//
//    }

    public void chooseFaction() {
        eb.consumer("tetris.game.faction.choose", message -> {
            String faction = message.body().toString();
            message.reply(faction);
        });
    }
    // TODO: pass faction to DB.

    public void getFactionInfo() {


        eb.consumer("tetris.game.faction.get", message -> {
            String faction = message.body().toString();
            message.reply(null);
        });

    }
    // TODO: get faction from DB.

    public void sendBlockOneByOne(Game game) {
        eb.send("tetris.infoBackend.test", Json.encode(game));

        System.out.println(game.getPlayers().get(0));
    }

    public void homeScreen() {
    }
}
