package game.api.webapi;


import game.Game;
import game.api.jdbcinteractor.Database;
import game.events.event.AbilityReset;
import game.events.event.Tornado;
import game.events.event.Trigger;
import game.player.Player;
import game.player.playfield.Playfield;
import game.player.playfield.block.Block;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.pmw.tinylog.Logger;

import java.util.List;

public class Routes extends AbstractVerticle {
    private EventBus eb;
    private GameController controller;
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


    public Routes(GameController gameController) {
        controller = gameController;
        this.game = controller.getGame();
    }

    private void addConsumers(){
//        homescreen
        eb.consumer("tetris-21.socket.homescreen",this::getPlayerName);

//        shop get gold
        eb.consumer("tetris-21.socket.gold", this::getGold);

//        gameStart
        eb.consumer("tetris-21.socket.gameStart.faction", this::getFaction);

//        choose faction
        eb.consumer("tetris-21.socket.faction.choose", this::chooseFaction);





//        playfield
        eb.consumer("tetris-21.socket.gamestart",this::ImReady);

        eb.consumer("tetris-21.socket.battleField.getNewBlock",this::getNewBlock);
        eb.consumer("tetris-21.socket.battleField.rotate",this::rotateBlock);
        eb.consumer("tetris-21.socket.battleField.blockOnField",this::blockOnField);
        eb.consumer("tetris-21.socket.battleField.evenements",this::evenements);

//        eb.consumer("tetris-21.socket.sendBlock",this::sendBlockOneByOne);

        // Login
        eb.consumer("tetris-21.socket.login", this::login);

        // Make login
        eb.consumer("tetris-21.socket.login.make", this::makeLogin);

        // Check username
        eb.consumer("tetris-21.socket.login.username", this::checkUsername);

        // May login
        //eb.consumer("tetris-21.socket.login.may", this::mayLogin);

    }

    private void evenements(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());

        String playerName1 = userMessage.getString("playerName1");
        String playerName2 = userMessage.getString("playerName2");
        String evenementName = userMessage.getString("evenement");
        System.out.println(evenementName);

        List<List<Integer>> playfield1 = switchCaseEvenements(playerName1, evenementName);
        List<List<Integer>> playfield2 = switchCaseEvenements(playerName2, evenementName);

        JsonObject json = new JsonObject();
        json.put(playerName1 , playfield1);
        json.put(playerName2 , playfield2);

        System.out.println(json);
        message.reply(json.encode());
    }


    private List<List<Integer>> switchCaseEvenements(String playerName, String evenementName){

        Playfield playfield = getPlayfieldByPlayerName(playerName);

        Logger.info(evenementName+ " \n " + playerName + " \n " + playfield);

        Trigger trigger = null;
        switch (evenementName) {
            case "tornado":
                trigger = Trigger.TIME;
                Tornado tornado = new Tornado(trigger,playfield);
                tornado.activate();
                Logger.info("Tornaadddoooooo");
                break;
            case "abilityReset":
                trigger = Trigger.TIME;
                AbilityReset abilityReset = new AbilityReset(trigger, playfield);
                abilityReset.activate();
                break;
            case "blabla":
                // todo
                break;
            case "meer blabla":
                // todo
                break;
            default:
                Logger.info("NO evenement");
                break;
        }

        Logger.info(playerName + " \n " + playfield);
        return playfield.getPlayfield();
    }

    private void blockOnField(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        JsonObject player = userMessage.getJsonObject("player");

        String playerName = player.getString("name");

        JsonObject pos = player.getJsonObject("pos");
        Integer xPosition = pos.getInteger("x");
        Integer yPosition = pos.getInteger("y");

        Playfield playfield = getPlayfieldByPlayerName(playerName);
        playfield.putOnPlayField(xPosition, yPosition);


        message.reply(Json.encode(playfield.getPlayfield()));
    }


    private void getNewBlock(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String playername = userMessage.getString("playername");

       Playfield playfield = getPlayfieldByPlayerName(playername);
        Block block = playfield.newBlock();
        int score = playfield.getScore();
        int points = playfield.getPoints();

        JsonObject json = new JsonObject();
        json.put("block", block.getBlock());
        json.put("color", block.getColor());
        json.put("score", score);
        json.put("points", points);

        message.reply(json.encode());
    }

    private Playfield getPlayfieldByPlayerName(String playerName){
        Playfield playfield = null;
        for (Player player:game.getPlayers()) {
            if (player.getName().equals(playerName)){
                playfield = player.getPlayfieldByName(playerName);
            }
        }
        return playfield;
    }


    private void rotateBlock(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String playername = userMessage.getString("playerName");

        Playfield playfield = getPlayfieldByPlayerName(playername);
        playfield.getCurrentBlock().rotateLeft();
        Block rotateBlock = playfield.getCurrentBlock();

        message.reply(Json.encode(rotateBlock));
    }


    private void getGold(Message message) {
        System.out.println(message.body());
        int UserId = (int) message.body();
        Database.getDB().getConsumerHandlers(controller).getGold(UserId, eb);

        message.reply("I will get the gold :D");
    }

    private void login(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String username = userMessage.getString("username");
        String password = userMessage.getString("password");

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
        String playername = userMessage.getString("playername");

        message.reply(username);
        Database.getDB()
                .getConsumerHandlers(controller)
                .makeUser(username, email, password, eb);

        Database.getDB()
                .getConsumerHandlers(controller)
                .makePlayer(playername);
    }

    private void checkUsername(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String username = userMessage.getString("username");
        Database.getDB()
                .getConsumerHandlers(controller)
                .checkUsername(username, eb);
        message.reply(username);
    }

    private void getPlayerName(Message message) {
        String username = message.body().toString();
        System.out.println(username);
        message.reply(username);

        Database.getDB()
                .getConsumerHandlers(controller)
                .getPlayerInfo(username, eb);
    }

    private void getFaction(Message message) {
        int playerId = (int) message.body();
        System.out.println(playerId);
        message.reply("going to look for " + playerId);

        Database.getDB()
                .getConsumerHandlers(controller)
                .getBasic(playerId, eb);

    }


    public void chooseFaction(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        System.out.println(userMessage);
        int factionId = userMessage.getInteger("factionId");
        int userId = Integer.parseInt(userMessage.getString("userId"));


        Database.getDB().getConsumerHandlers(controller).insertFaction( factionId, userId);
        message.reply("Lets add factionId: " + factionId + " to userId: " + userId);
    }





//    private void updateGame(Message message) {
//        JsonObject object =  new JsonObject(message.body().toString());
//
//        System.out.println(object);
//        int number = game.getPlayers().indexOf(object.getString("player"));
//        player = game.getPlayers().get(number);
//        System.out.println(player.getPlayfieldByName().getPlayfieldByName().get(0));
//        player.getPlayfieldByName().getPlayfieldByName().get(0).updateScore(Integer.parseInt(object.getString("score")));
//        System.out.println(Integer.parseInt(object.getString("score")));
//        System.out.println(player.getPlayfieldByName().getPlayfieldByName().get(0));
//        game.getPlayers().get(number).getPlayfieldByName().getPlayfieldByName().get(0).setScore();
//
//        message.reply(makeObjectJson(game));
//    }


    private void ImReady(Message message){
        System.out.println(game);
        eb.send("tetris-21.socket.game", Json.encode(game));
        message.reply("okey");

    }

    //
//    public void battleFieldBlockPositioning() {
//        eb.consumer("tetris.infoBackend.BattleField.positionBlock", message -> {
//            game.getPlayers().get(1).getPlayfieldByName().getPlayfieldByName().get(1);
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
