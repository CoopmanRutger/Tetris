package game.api.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.Game;
import game.api.jdbcinteractor.ConsumerHandlers;
import game.api.jdbcinteractor.Database;
import game.player.Player;
import game.player.login.Login;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import javax.xml.crypto.Data;
import java.util.concurrent.TimeUnit;

public class Routes extends AbstractVerticle {
    private EventBus eb;
    private GameController controller;
    private Game game;
    private Player player;

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


    public Routes(GameController gameController) {
        controller = gameController;
        this.game = controller.getGame();
    }

    public void addConsumers(){
//        homescreen
        eb.consumer("tetris-21.socket.homescreen",this::getPlayerName);


        // faction yes/no
        eb.consumer("tetris-21.socket.faction", this::getFaction);


//        choose faction
        eb.consumer("tetris-21.socket.faction.choose", this::chooseFaction);

//        gameStart
//        eb.consumer()


//        playfield
        eb.consumer("tetris-21.socket.gamestart",this::ImReady);
//        eb.consumer("tetris.infoBackend.game",this::sendBack);

        eb.consumer("tetris-21.socket.updateGame",this::updateGame);
        eb.consumer("tetris-21.socket.game",this::sendBlockOneByOne);

        // Login
        eb.consumer("tetris-21.socket.login", this::login);

        // Make login
        eb.consumer("tetris-21.socket.login.make", this::makeLogin);

        // May login
        //eb.consumer("tetris-21.socket.login.may", this::mayLogin);

    }

    private void login(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String username = userMessage.getString("username");
        String password = userMessage.getString("password");
//        Login getLogin = new Login();
//        getLogin.getPasswordFromDb(username, eb);
        message.reply(username);
        Database.getDB()
                .getConsumerHandlers(controller)
                .checkPassword(username, password, eb);
    }

    private void makeLogin(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String username = userMessage.getString("username");
        String email = userMessage.getString("email");
        String password = userMessage.getString("password");
//        Login login = new Login();
//        login.makeLogin(username, email, password, eb);
//        message.reply(canLogin);
        message.reply(username);
        Database.getDB()
                .getConsumerHandlers(controller)
                .makeUser(username, email, password, eb);
    }

//    private void mayLogin(Message message) {
//        message.reply(login);
//    }

    private void getPlayerName(Message message) {
        String username = message.body().toString();
        System.out.println(username);
        message.reply(username);

        Database.getDB()
                .getConsumerHandlers(controller)
                .getPlayerInfo(username, eb);
    }

    private void getFaction(Message message) {
        String playername = message.body().toString();
        System.out.println(playername);
        message.reply(playername);

        Database.getDB()
                .getConsumerHandlers(controller)
                .getFaction(playername, eb);

    }


    public void chooseFaction(Message message) {
        String faction = message.body().toString();
        System.out.println(faction);
//        TODO: pass faction to DB.
        message.reply(faction);
    }





    private void updateGame(Message message) {
        JsonObject object =  new JsonObject(message.body().toString());

        int number = game.getPlayers().indexOf(object.getString("player"));
        player = game.getPlayers().get(number);
        System.out.println(player.getPlayfields().getPlayfields().get(0));
        player.getPlayfields().getPlayfields().get(0).updateScore(Integer.parseInt(object.getString("score")));
        System.out.println(Integer.parseInt(object.getString("score")));
        System.out.println(player.getPlayfields().getPlayfields().get(0));
//        game.getPlayers().get(number).getPlayfields().getPlayfields().get(0).setScore();

        message.reply(makeObjectJson(game));
    }

    private String makeObjectJson(Object info){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(info);
            return json;
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }


    public void sendBlockOneByOne(Message message) {
        System.out.println(message.body());

//        eb.send("tetris.infoBackend.nextBlock", Json.encode(game));

    }



    private void ImReady(Message message){
        System.out.println(message.body());
        eb.send("tetris-21.socket.game", makeObjectJson(game));
        message.reply("okey");

    }

//    private void sendBack(Message message) {
//        System.out.println(message);
//        JsonObject json = new JsonObject();
//        json.put("yeet","yeet");
//        message.reply(json.encode());
//    }

    //
//    public void battleFieldBlockPositioning() {
//        eb.consumer("tetris.infoBackend.BattleField.positionBlock", message -> {
//            game.getPlayers().get(1).getPlayfields().getPlayfields().get(1);
//            String m = message.body().toString();
//                    .put(OnPlayField(1, 1, new Block("block", TypesOfBlocks.lBlock, Color.RED)));
//            message.reply(m);
//            sendBlockOneByOne(game);
//        });
//
//    }


//    public void getFactionInfo() {
//
//            String m = message.body().toString();
//        eb.consumer("tetris.game.faction.get", message -> {
//        });
//            message.reply(null);
//    }
//    // TODO: get faction from DB.
//

}
