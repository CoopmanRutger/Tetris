package game.api.webapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.Game;
import game.api.jdbcinteractor.Database;
import game.api.jdbcinteractor.InfoStrings;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.h2.message.Trace;
import org.pmw.tinylog.Logger;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Routes extends AbstractVerticle {
    private EventBus eb;
    private final GameController controller;
    private Game game;
    //private Player player;
    private final InfoStrings strings = new InfoStrings();

    public Routes(final GameController gameController) {
        controller = gameController;
        //        this.game = controller.getGame();
    }

    public void rootHandler(final RoutingContext routingContext) {
        final HttpServerResponse response = routingContext.response();
        response.setChunked(true);
        response.write("hello tetris");
        response.end();
    }

    @Override
    public void start() {
        eb = vertx.eventBus();
        addConsumers();
    }

    public void addConsumers() {
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
        eb.consumer("tetris-21.socket.updateScore", this::updateScore);

        //        eb.consumer("tetris-21.socket.updateGame",this::updateGame);
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

    private void receiveGold(final Message message) {
        //System.out.println(message.body());
        Logger.info(message.body());
        final int userId = (int) message.body();
        Database.getDB().getConsumerHandlers(controller).receiveGold(userId, eb);

        message.reply("I will get the gold :D");
    }

    private void updateScore(final Message message) {
        //System.out.println(message.body());
        Logger.info(message.body());
        final JsonObject json = new JsonObject();
        json.put("playerName", "test");
        json.put("addToScore", 100);
        json.put("addlines", 1);
        message.reply(json.encode());
    }

    private void login(final Message message) {
        final JsonObject userMessage = new JsonObject(message.body().toString());
        final String username = userMessage.getString(strings.getUsernameString());
        final String password = userMessage.getString(strings.getPasswordString());

        message.reply(username);
        Database.getDB()
            .getConsumerHandlers(controller)
            .checkPassword(username, password, eb);
    }

    private void makeLogin(final Message message) {
        final JsonObject userMessage = new JsonObject(message.body().toString());
        final String username = userMessage.getString(strings.getUsernameString());
        final String email = userMessage.getString("email");
        final String password = userMessage.getString(strings.getPasswordString());
        final String playername = userMessage.getString("playername");

        message.reply(username);
        Database.getDB()
            .getConsumerHandlers(controller)
            .makeUser(username, email, password, eb);

        Database.getDB()
            .getConsumerHandlers(controller)
            .makePlayer(playername);
    }

    private void checkUsername(final Message message) {
        final JsonObject userMessage = new JsonObject(message.body().toString());
        final String username = userMessage.getString(strings.getUsernameString());
        Database.getDB()
            .getConsumerHandlers(controller)
            .checkUsername(username, eb);
        message.reply(username);
    }

    //    private void mayLogin(Message message) {
    //        message.reply(login);
    //    }

    private void receivePlayerName(final Message message) {
        final String username = message.body().toString();
        //System.out.println(username);
        Logger.info(username);
        message.reply(username);
        Database.getDB()
            .getConsumerHandlers(controller)
            .receivePlayerInfo(username, eb);
    }

    private void receiveFaction(final Message message) {
        final int playerId = (int) message.body();
        //System.out.println(playerId);
        Logger.info(playerId);
        message.reply("going to look for " + playerId);

        Database.getDB()
            .getConsumerHandlers(controller)
            .receiveBasic(playerId, eb);

    }


    public void chooseFaction(final Message message) {
        final JsonObject userMessage = new JsonObject(message.body().toString());
        //System.out.println(userMessage);
        Logger.info(userMessage);
        final int factionId = userMessage.getInteger("factionId");
        final int userId = Integer.parseInt(userMessage.getString("userId"));


        Database.getDB().getConsumerHandlers(controller).insertFaction(factionId, userId);
        message.reply("Lets add factionId: " + factionId + " to userId: " + userId);
    }


    //    private void updateGame(Message message) {
    //        JsonObject object =  new JsonObject(message.body().toString());
    //
    //        System.out.println(object);
    //        int number = game.getPlayers().indexOf(object.getString("player"));
    //        player = game.getPlayers().get(number);
    //        System.out.println(player.getPlayfields().getPlayfields().get(0));
    //        player.getPlayfields().getPlayfields().get(0).updateScore(Integer.parseInt(object.getString("score")));
    //        System.out.println(Integer.parseInt(object.getString("score")));
    //        System.out.println(player.getPlayfields().getPlayfields().get(0));
    //        game.getPlayers().get(number).getPlayfields().getPlayfields().get(0).setScore();
    //
    //        message.reply(makeObjectJson(game));
    //    }


    private String makeObjectJson(final Object info) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            final Trace logger = null;
            logger.debug("your log message here");

            // TODO: 17/12/2018  oplossen tiny logger
        }
        return null;
    }

    public void sendBlockOneByOne(final Message message) {
        //System.out.println(message.body());
        Logger.info(message.body());

        //        eb.send("tetris.infoBackend.nextBlock", Json.encode(game));

    }


    private void imReady(final Message message) {
        //System.out.println(message.body());
        Logger.info(message.body());
        eb.send("tetris-21.socket.game", makeObjectJson(game));
        message.reply("okey");

    }

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
    //        String m = message.body().toString();
    //        eb.consumer("tetris.game.faction.get", message -> {
    //        });
    //        message.reply(null);
    //    }
    //    // TODO: get faction from DB.
    //

}
