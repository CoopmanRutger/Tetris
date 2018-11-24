package game.api.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.Game;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.RoutingContext;
import jdk.nashorn.internal.parser.JSONParser;

import java.awt.*;

public class Routes extends AbstractVerticle {
    private EventBus eb;
    private Game game;

    void rootHandler(RoutingContext routingContext){
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("hello tetris");
        response.end();

    }
    public void start(){
        eb = vertx.eventBus();
        addConsumers();
    }


    public Routes() {
        Server server = new Server();
        this.game = server.getGame();
    }

    public void addConsumers(){
        eb.consumer("tetris.infoBackend.gamestart",this::ImReady);
//        eb.consumer("tetris.infoBackend.game",this::sendBack);
    }

    private void ImReady(Message message){
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(game);
            eb.send("tetris.infoBackend.game", json);
//            message.reply(json);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }

    private void sendBack(Message message) {
        System.out.println(message);
        JsonObject json = new JsonObject();
        json.put("yeet","yeet");
        message.reply(json.encode());
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
//    public void chooseFaction() {
//            String faction = message.body().toString();
//        eb.consumer("tetris.game.faction.choose", message -> {
//            message.reply(faction);
//        });
//    }
//    // TODO: pass faction to DB.
//    public void getFactionInfo() {
//
//            String m = message.body().toString();
//        eb.consumer("tetris.game.faction.get", message -> {
//        });
//            message.reply(null);
//    }
//    // TODO: get faction from DB.
//
    public void sendBlockOneByOne(Game game) {
        eb.send("tetris.infoBackend.test", Json.encode(game));

        System.out.println(game.getPlayers().get(0));
    }

    public void homeScreen() {
    }
}
