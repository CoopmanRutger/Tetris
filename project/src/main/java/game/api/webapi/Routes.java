package game.api.webapi;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.awt.*;

public class Routes {
//    private EventBus eb;
//    private Game game;

    void rootHandler(RoutingContext routingContext){
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("hello tetris");
        response.end();
    }


    public void battleFieldStart(){
            String m = message.body().toString();
        eb.consumer("tetris.infoBackend.BattleField",message -> {
            message.reply(m);
            sendBlockOneByOne(game);
        });
    }

    public void battleFieldBlockPositioning() {
        eb.consumer("tetris.infoBackend.BattleField.positionBlock", message -> {
            game.getPlayers().get(1).getPlayfields().getPlayfields().get(1)
            String m = message.body().toString();
                    .putOnPlayField(1, 1, new Block("Lblock", TypesOfBlocks.lBlock, Color.RED));
            message.reply(m);
            sendBlockOneByOne(game);
        });

    }
    public void chooseFaction() {
            String faction = message.body().toString();
        eb.consumer("tetris.game.faction.choose", message -> {
            message.reply(faction);
        });
    }
    // TODO: pass faction to DB.
    public void getFactionInfo() {

            String m = message.body().toString();
        eb.consumer("tetris.game.faction.get", message -> {
        });
            message.reply(null);
    }
    // TODO: get faction from DB.

    public void sendBlockOneByOne(Game game) {
        eb.send("tetris.infoBackend.test", Json.encode(game));

        System.out.println(game.getPlayers().get(0));
    }
}
