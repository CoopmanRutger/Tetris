package game.api.jdbcinteractor;

import game.api.webapi.GameController;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import org.mindrot.jbcrypt.BCrypt;
import org.pmw.tinylog.Logger;

/**
 * @author Remote Access Tetris aka RAT
 */
public class ConsumerHandlers {

    private JDBCClient jdbcClient;
    //CHECKSTYLE:OFF
    private GameController controller; // NOPMD
    //CHECKSTYLE:ON
    private final InfoStrings strings = new InfoStrings();
    private final SqlLines sqlLines = new SqlLines();


    public ConsumerHandlers() {
        this(null);
    }

    ConsumerHandlers(final GameController controller) {
        jdbcClient = ConnectionDatabase.getJdbcClient();
        this.controller = controller;
    }


    public void receivePlayerInfo(final String username, final EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(sqlLines.getUser(), params[0],
            res -> {
                final JsonObject player = new JsonObject();
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    player.put("userId", rs.getResults().get(0).getInteger(0));
                    player.put(strings.getUsernameString(), rs.getResults().get(0).getString(1));
                    player.put("playerId", rs.getResults().get(0).getInteger(5));
                    player.put("playerName", rs.getResults().get(0).getString(6));
                    //System.out.println(player);
                    insertRandomFaction(rs.getResults().get(0).getInteger(0));
                    controller.setPlayer1(rs.getResults().get(0).getString(6));
                    controller.setUsername1(player.getString(rs.getResults().get(0).getString(1)));

                } else {
                    Logger.warn("Could not get player info from DB: ", res.cause());
                }

                eb.send(strings.getPlayerInfoSockerString(), player.encode());
            }
        );
    }

    private void insertRandomFaction(final int userId) {
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(sqlLines.getMakeRandomFaction(), params[0],
            res -> {
                if (res.succeeded()) {
                    //System.out.println("check");
                    Logger.info("check");
                } else {
                    Logger.warn(strings.getDbInfoString(), res.cause());
                }
            });
    }

    public void receiveGold(final int userId, final EventBus eb) {
        final JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(sqlLines.getGold(), params[0],
            res -> {
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    reponse.put("gold", rs.getResults().get(0));
                } else {
                    Logger.warn(strings.getDbInfoString(), res.cause());
                }
                eb.send("tetris-21.socket.gold.get", reponse);
            });
    }


    public void insertFaction(final int factionNr, final int userId) {
        final JsonArray params = new JsonArray().add(factionNr).add(userId).add(userId);
        jdbcClient.updateWithParams(sqlLines.getMakeFaction(), params, res -> {
            //System.out.println(res.succeeded());
            Logger.info(res.succeeded());
            if (res.succeeded()) {
                final UpdateResult updateResult = res.result();
                //System.out.println(updateResult);
                Logger.info(updateResult);
                if (updateResult.getUpdated() > 0) {
                    Logger.info(" insert faction into DB factionNr:" + factionNr + " and userId " + userId);
                }
            } else {

                Logger.warn("Could not insert into faction.", res.cause());
            }
        });
    }


    private void makeNewplayerUsername(final String username, final EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(sqlLines.getMakeUser(), params[0],
            res -> {
                final JsonObject player = new JsonObject();
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    player.put(strings.getPlayerString(), rs.getResults().get(0));
                    //System.out.println(rs.getResults().get(0).getString(1));
                    Logger.info(rs.getResults().get(0).getString(1));

                    //System.out.println("result!!! " + player);
                    Logger.info("result!!! " + player);
                } else {
                    Logger.warn(strings.getDbInfoString(), res.cause());
                }

                controller.setUsername1(player.getJsonArray(strings.getPlayerString()).getString(1));
                eb.send(strings.getPlayerInfoSockerString(), player.encode());
            });
    }


    public void receiveBasic(final int playerId, final EventBus eb) {
        final JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playerId)};
        jdbcClient.queryWithParams(sqlLines.getBasic(), params[0],
            res -> {
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    //System.out.println("rs: " + rs.getResults());
                    Logger.info("rs: " + rs.getResults());
                    //System.out.println(rs.getResults());
                    Logger.info(rs.getResults());
                    final JsonArray jsonArray = rs.getResults().get(0);
                    //System.out.println(rs.getColumnNames());
                    Logger.info(rs.getColumnNames());
                    //System.out.println(jsonArray);
                    Logger.info(jsonArray);

                    if (jsonArray.getString(1) != null) {
                        reponse.put("FactionNr", jsonArray.getInteger(0));
                        reponse.put("FactionName", jsonArray.getString(1));
                        reponse.put("ClanNr", jsonArray.getInteger(2));
                        reponse.put("ClanName", jsonArray.getString(3));
                    }
                    //System.out.println("result    !!! " + reponse);
                    Logger.info("result    !!! " + reponse);
                } else {
                    Logger.warn(strings.getDbInfoString(), res.cause());
                }

                eb.send("tetris-21.socket.gameStart.faction.get", reponse);
            });
    }

    public void checkPassword(final String username, final String password, final EventBus eb) {
        final JsonObject passwordFromDb = new JsonObject();
        final JsonObject passwordResult = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(sqlLines.getPassword(), params[0], res -> {
            if (res.succeeded()) {
                final ResultSet rs = res.result();
                passwordFromDb.put(strings.getPasswordString(), rs.getResults().get(0).getString(0));
                final boolean samePassword = BCrypt.checkpw(password,
                    passwordFromDb.getString(strings.getPasswordString()));
                passwordResult.put(strings.getCanLoginString(), String.valueOf(samePassword));
            } else {
                Logger.warn(strings.getDbInfoString(), res.cause());
                passwordResult.put(strings.getCanLoginString(), strings.getFalseString());
            }
            eb.send("tetris-21.socket.login.server", passwordResult.getString(strings.getCanLoginString()));
        });
    }

    public void makeUser(final String username, final String email, final String password, final EventBus eb) {
        final JsonObject couldLogin = new JsonObject();
        final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        final JsonArray[] params = {new JsonArray()
            .add(username)
            .add(email)
            .add(hashedPassword)
            .add(0)};
        jdbcClient.queryWithParams(sqlLines.getMakeLogin(), params[0], res -> {
            if (res.succeeded()) {
                couldLogin.put(strings.getRegisterString(), strings.getTrueString());
            } else {
                couldLogin.put(strings.getRegisterString(), strings.getFalseString());
                Logger.warn("Could not make login: ", res.cause());
            }
            eb.send("tetris-21.socket.login.make.server", couldLogin.getString(strings.getRegisterString()));
        });
    }

    public void makePlayer(final String playername) {
        final JsonObject couldMakePlayername = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playername)};
        jdbcClient.queryWithParams(sqlLines.getMakePlayerName(), params[0], res -> {
            if (res.succeeded()) {
                Logger.info("Player was made.");
            } else {
                Logger.warn("Could not make player.");
            }
        });
    }

    public void checkUsername(final String username, final EventBus eb) {
        final JsonObject loginExists = new JsonObject();
        final JsonArray[] params = {new JsonArray()
            .add(username)};
        jdbcClient.queryWithParams(sqlLines.getUserExists(), params[0], res -> {
            if (res.succeeded()) {
                final ResultSet rs = res.result();
                if (rs.getResults().size() == 0) {
                    loginExists.put(strings.getUsernameString(), strings.getFalseString());
                } else {
                    loginExists.put(strings.getUsernameString(), strings.getTrueString());
                }
            } else {
                Logger.warn("Could not check username: ", res.cause());
            }
            eb.send("tetris-21.socket.login.username.server", loginExists.getString(strings.getUsernameString()));
        });
    }


    //    public boolean chooseFaction(String playerName, String clanName) {
    //        final boolean[] happened = new boolean[1];
    //        JsonArray params = new JsonArray().add(getClanNr(clanName)).add(getUserid(playerName));
    //        jdbcClient.updateWithParams(chooseFaction, params,res -> {
    //            if (res.succeeded()) {
    //                UpdateResult updateResult = res.result();
    //                if (updateResult.getUpdated() > 0) {
    //                    happened[0] = false;
    //                    Logger.warn("Could not insert into faction.");
    //                } else {
    //                    happened[0] = true;
    //                }
    //            } else {
    //                happened[0] = false;
    //                Logger.warn("Could not insert into faction.", res.cause());
    //            }
    //        });
    //
    //        return happened[0];
    //    }


    //    private String getUserid(String playerName) {
    //        final String[] userId = new String[1];
    //        JsonArray params = new JsonArray().add(playerName);
    //        jdbcClient.queryWithParams(getUser, params, res -> {
    //            if (res.succeeded()) {
    //                ResultSet rs = res.result();
    //                List<JsonObject> rows = rs.getRows();
    //                for (JsonObject row : rows) {
    //                    userId[0] = row.getString("userId");
    //                }
    //            }
    //        });
    //        return userId[0];
    //    }
    //
    //    private String getClanNr(String clanName) {
    //        final String[] clanNr = new String[1];
    //        JsonArray params = new JsonArray().add(clanName);
    //        jdbcClient.queryWithParams(getClan, params, res -> {
    //            ResultSet rs = res.result();
    //            List<JsonObject> rows = rs.getRows();
    //            for (JsonObject row : rows) {
    //                clanNr[0] = row.getString("clanNr");
    //            }
    //        });
    //        return clanNr[0];
    //    }


}
