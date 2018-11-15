package game.api.webapi;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class Routes {
//    private EventBus eb;
//    private Game game;

    void rootHandler(RoutingContext routingContext){
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("hello tetris");
        response.end();
    }


//    public Routes() {
//        Vertx vertx = Vertx.vertx();
//        eb = vertx.eventBus();
//    }
//
//
//    public void homeScreen() {
//        JsonObject json = new JsonObject();
//        json.put("message", "hello");
//        eb.send("tetris.infoBackend.homescreen", json.encode());
//
//    }
//
//    public void BattleFieldStart(){
//        eb.consumer("tetris.infoBackend.BattleField",message -> {
//            String m = message.body().toString();
//            message.reply(m);
//            sendBlockOneByOne(game);
//        });
//    }
//
//    public void chooseFaction() {
//        eb.consumer("tetris.game.faction.choose", message -> {
//            String faction = message.body().toString();
//            message.reply(faction);
//        });
//    }
//    // TODO: pass faction to DB.
//
//    public void getFactionInfo() {
//        eb.consumer("tetris.game.faction.get", message -> {
//            String m = message.body().toString();
//            message.reply(null);
//        });
//    }
//    // TODO: get faction from DB.
//
//    public void sendBlockOneByOne(Game game) {
//        eb.send("tetris.infoBackend.test", Json.encode(game));
//
//        System.out.println(game.getPlayers().get(0));
//    }
}
