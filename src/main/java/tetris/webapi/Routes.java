package tetris.webapi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.pmw.tinylog.Logger;
import tetris.Game;
import tetris.api.jdbcinteractor.Database;
import tetris.events.event.AbilityReset;
import tetris.events.event.Tornado;
import tetris.events.event.Trigger;
import tetris.player.Player;
import tetris.player.hero.ability.Ability;
import tetris.player.hero.ability.CheeringCrowd;
import tetris.player.hero.ability.Joker;
import tetris.player.playfield.Playfield;
import tetris.player.playfield.block.Block;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Routes extends AbstractVerticle {

    private static final String NEWLINE = "\n";
    private static final String PLAYERNAME = "playername";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private EventBus eb;
    private GameController controller;
    private Game game;

    public Routes(GameController gameController) {
        controller = gameController;
        this.game = controller.getGame();
    }

    void rootHandler(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("hello tetris");
        response.end();
    }

    @Override
    public void start() {
        eb = vertx.eventBus();
        addConsumers();
    }

    private void addConsumers() {
        //        homescreen
        eb.consumer("tetris-21.socket.homescreen", this::receivePlayerName);

        //        shop get gold
        eb.consumer("tetris-21.socket.gold", this::receiveGold);

        //        gameStart
        eb.consumer("tetris-21.socket.gameStart.faction", this::receiveFaction);

        //        choose faction
        eb.consumer("tetris-21.socket.faction.choose", this::chooseFaction);


        //        playfield
        eb.consumer("tetris-21.socket.gamestart", this::imReady);

        eb.consumer("tetris-21.socket.battleField.getNewBlock", this::receiveNewBlock);
        eb.consumer("tetris-21.socket.battleField.rotate", this::rotateBlock);
        eb.consumer("tetris-21.socket.battleField.blockOnField", this::blockOnField);
        eb.consumer("tetris-21.socket.battleField.evenements", this::evenements);

        eb.consumer("tetris-21.socket.battleField.abilities", this::abilities);

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

    private void timer(int seconds) {
        final int[] counter = {0};
        counter[0] = seconds;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                counter[0]--;
                JsonObject json = new JsonObject();
                json.put("timeInSeconds", counter[0]);
                eb.send("tetris-21.socket.battleField.timer", json.encode());
                Logger.info(counter[0]);
                if (counter[0] <= 0) {
                    timer.cancel();
                    // TODO: check for winner.
                }
            }
        }, 0, 1000);
    }

    private void abilities(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String playername = userMessage.getString("attacker");
        String otherUser = userMessage.getString("victim");
        String ability = userMessage.getString("ability");

        String couldActivate = null;

        if ("CheeringCrowd".equals(ability)) {
            CheeringCrowd cheeringCrowd = new CheeringCrowd(getPlayfieldByPlayerName(playername));
            couldActivate = String.valueOf(cheeringCrowd.activate());
            timeTheEvent((long) 20000, cheeringCrowd);
        } else if ("Joker".equals(ability)) {
            Joker joker = new Joker(getPlayfieldByPlayerName(otherUser));
            couldActivate = String.valueOf(joker.activate());
            timeTheEvent((long) 10000, joker);
        }

        message.reply(couldActivate);
    }

    private void timeTheEvent(Long seconds, Ability ability) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ability.stopAction();
                letKnowThatTheAbilityIsDone(ability);
            }
        }, seconds);
    }

    private void letKnowThatTheAbilityIsDone(Ability ability) {
        JsonObject json = new JsonObject();
        String abilityName = ability.getName();
        json.put(abilityName, "done");
        eb.send("tetris-21.socket.battleField.abilities.done", json.encode());
    }

    private void evenements(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());

        String playerName1 = userMessage.getString("playerName1");
        String playerName2 = userMessage.getString("playerName2");
        String evenementName = userMessage.getString("evenement");
        Logger.info(evenementName);

        List<List<Integer>> playfield1 = switchCaseEvenements(playerName1, evenementName);
        List<List<Integer>> playfield2 = switchCaseEvenements(playerName2, evenementName);

        JsonObject json = new JsonObject();
        json.put(playerName1, playfield1);
        json.put(playerName2, playfield2);

        Logger.info(json);
        message.reply(json.encode());
    }


    private List<List<Integer>> switchCaseEvenements(String playerName, String evenementName) {

        Playfield playfield = getPlayfieldByPlayerName(playerName);

        Logger.info(evenementName + NEWLINE + playerName + NEWLINE + playfield);

        Trigger trigger = null;
        switch (evenementName) {
            case "tornado":
                trigger = Trigger.TIME;
                Tornado tornado = new Tornado(trigger, playfield);
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

        Logger.info(playerName + " NEWLINE " + playfield);
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


    private void receiveNewBlock(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String playername = userMessage.getString(PLAYERNAME);

        Playfield playfield = getPlayfieldByPlayerName(playername);
        Block block = playfield.newBlock();
        int score = playfield.getScore();
        int points = playfield.getPoints();
        int blockCounter = playfield.getCounter();
        final int lines = playfield.getScoreByName().getAmountOfLines();

        if (blockCounter % 5 == 0) {
            int playfieldSpeed = playfield.getPlayfieldSpeed();
            playfield.setGameSpeed(playfieldSpeed - 1);
        }

        JsonObject json = new JsonObject();
        json.put("block", block.getBlock());
        json.put("color", block.getColor());
        json.put("score", score);
        json.put("points", points);
        json.put("lines", lines);
        json.put("gameSpeed", playfield.getPlayfieldSpeed());

        message.reply(json.encode());
    }


    private Playfield getPlayfieldByPlayerName(String playerName) {
        Playfield playfield = null;
        for (Player player : game.getPlayers()) {
            if (player.getName().equals(playerName)) {
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


    private void receiveGold(Message message) {
        Logger.info(message.body());
        int userId = (int) message.body();
        Database.getDB().getConsumerHandlers(controller).receiveGold(userId, eb);

        message.reply("I will get the gold :D");
    }

    private void login(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String username = userMessage.getString(USERNAME);
        String password = userMessage.getString(PASSWORD);

        message.reply(username);
        Database.getDB()
            .getConsumerHandlers(controller)
            .checkPassword(username, password, eb);
    }

    private void makeLogin(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        String username = userMessage.getString(USERNAME);
        String email = userMessage.getString("email");
        String password = userMessage.getString(PASSWORD);
        String playername = userMessage.getString(PLAYERNAME);

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
        String username = userMessage.getString(USERNAME);
        Database.getDB()
            .getConsumerHandlers(controller)
            .checkUsername(username, eb);
        message.reply(username);
    }

    private void receivePlayerName(Message message) {
        String username = message.body().toString();
        Logger.info(username);
        message.reply(username);

        Database.getDB()
            .getConsumerHandlers(controller)
            .receivePlayerInfo(username, eb);
    }

    private void receiveFaction(Message message) {
        int playerId = (int) message.body();
        Logger.info(playerId);
        message.reply("going to look for " + playerId);

        Database.getDB()
            .getConsumerHandlers(controller)
            .receiveBasic(playerId, eb);

    }


    public void chooseFaction(Message message) {
        JsonObject userMessage = new JsonObject(message.body().toString());
        Logger.info(userMessage);
        int factionId = userMessage.getInteger("factionId");
        int userId = Integer.parseInt(userMessage.getString("userId"));


        Database.getDB().getConsumerHandlers(controller).insertFaction(factionId, userId);
        message.reply("Lets add factionId: " + factionId + " to userId: " + userId);
    }


    //    private void updateGame(Message message) {
    //        JsonObject object =  new JsonObject(message.body().toString());
    //
    //        System.out.println(object);
    //        int number = game.getPlayers().indexOf(object.getString("player"));
    //        player = game.getPlayers().get(number);
    //        System.out.println(player.getPlayfieldByName().getPlayfieldByName().get(0));
    //        player.getPlayfieldByName().getPlayfieldByName().get(0).updateScore
    //        (Integer.parseInt(object.getString("score")));
    //        System.out.println(Integer.parseInt(object.getString("score")));
    //        System.out.println(player.getPlayfieldByName().getPlayfieldByName().get(0));
    //        game.getPlayers().get(number).getPlayfieldByName().getPlayfieldByName().get(0).setScore();
    //
    //        message.reply(makeObjectJson(game));
    //    }


    private void imReady(Message message) {
        Logger.info(game);
        timer(180);
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
    // TODO: get faction from DB.
    //

}
