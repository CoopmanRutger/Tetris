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

    //private static final String CREATEUSER = "INSERT INTO USERS (Username, email) VALUES ( ?, null);";
    //private static final String GETCLAN = "SELECT * FROM clans WHERE name = ?";
    private static final String GETPASSWORD = "SELECT password FROM users WHERE username = ?";
    private static final String USEREXISTS = "select * from users where username = ?";

    private StringLines lines = new StringLines();
    private JDBCClient jdbcClient;
    private GameController controller;

    private final String getBasic = "SELECT * FROM factions "
        + "Left join clans on factions.factionnr = clans.factionnr "
        + "Left join factions_users ON factions.factionNr= factions_users.factionNr "
        + "Left Join users on factions_users.userid = users.userid "
        + " WHERE users.userid = ? ";

    //private final String chooseFaction = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";

    private final String getUser = "select * from users "
        + "left join players on users.userid = players.userid "
        + "WHERE username = ?";
    private final String makeLogin = "INSERT INTO users (username, email, password, gold) "
        + "VALUES (?, ?, ?, ?)";

    private final String makeFaction = "UPDATE FACTIONS_USERS "
        + "SET Factionnr = ?, userid= ? WHERE userid = ?";

    private final String getGold = "SELECT gold FROM USERS "
        + "WHERE Userid = ?";

    private final String makeRandomFaction = "insert into FACTIONS_USERS  ( factionnr, userid) "
        + "values( 5, ?)";
    private final String makePlayerName = "INSERT INTO players (userid, PLAYERNAME, xp, level)"
        + "VALUES ((select max(userid) from users), ?, 0 , 1)";

    public ConsumerHandlers() {
        this(null);
    }

    ConsumerHandlers(GameController controller) {
        jdbcClient = ConnectionDatabase.getJdbcClient();
        this.controller = controller;
    }


    public void receivePlayerInfo(String username, EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(getUser, params[0],
            res -> {
                final JsonObject player = new JsonObject();
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    player.put(lines.getUserid(), rs.getResults().get(0).getInteger(0));
                    player.put(lines.getUsername(), rs.getResults().get(0).getString(1));
                    player.put("playerId", rs.getResults().get(0).getInteger(5));
                    player.put("playerName", rs.getResults().get(0).getString(6));
                    Logger.info(player);
                    insertRandomFaction(rs.getResults().get(0).getInteger(0));
                    controller.getPlayer1().setPlayer(rs.getResults().get(0).getString(6));
                    controller.getPlayer1().setUsername(player.getString(rs.getResults().get(0).getString(1)));

                } else {
                    Logger.warn("Could not get player info from DB: ", res.cause());
                }

                eb.send(lines.getSocketPlayerInfo(), player.encode());
            }
        );
    }

    private void insertRandomFaction(int userId) {
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(makeRandomFaction, params[0],
            res -> {
                if (res.succeeded()) {
                    Logger.info("check");

                } else {
                    Logger.warn(lines.getDbInfo(), res.cause());
                }
            });
    }

    public void receiveGold(int userId, EventBus eb) {
        JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(getGold, params[0],
            res -> {
                if (res.succeeded()) {
                    ResultSet rs = res.result();
                    reponse.put("gold", rs.getResults().get(0));
                } else {
                    Logger.warn(lines.getDbInfo(), res.cause());
                }
                eb.send("tetris-21.socket.gold.get", reponse);
            });
    }


    public void insertFaction(int factionNr, int userId) {
        JsonArray params = new JsonArray().add(factionNr).add(userId).add(userId);
        jdbcClient.updateWithParams(makeFaction, params, res -> {
            Logger.info(res.succeeded());
            if (res.succeeded()) {
                UpdateResult updateResult = res.result();
                Logger.info(updateResult);
                if (updateResult.getUpdated() > 0) {
                    Logger.info(" insert faction into DB factionNr:" + factionNr + " and userId " + userId);
                }
            } else {

                Logger.warn("Could not insert into faction.", res.cause());
            }
        });
    }


    //    private void makeNewplayerUsername(String username, EventBus eb) {
    //        final JsonArray[] params = {new JsonArray().add(username)};
    //        jdbcClient.queryWithParams(CREATEUSER, params[0],
    //            res -> {
    //                JsonObject player = new JsonObject();
    //                if (res.succeeded()) {
    //                    ResultSet rs = res.result();
    //                    player.put(lines.getPlayer(), rs.getResults().get(0));
    //                    System.out.println(rs.getResults().get(0).getString(1));
    //
    //                    System.out.println("result!!! " + player);
    //                } else {
    //                    Logger.warn(lines.getDbInfo(), res.cause());
    //                }
    //
    //                controller.getPlayer1().setUsername(player.getJsonArray(lines.getPlayer()).getString(1));
    //                eb.send(lines.getSocketPlayerInfo(), player.encode());
    //            });
    //    }


    public void receiveBasic(int playerId, EventBus eb) {
        JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playerId)};
        jdbcClient.queryWithParams(getBasic, params[0],
            res -> {
                if (res.succeeded()) {
                    ResultSet rs = res.result();
                    Logger.info("rs: " + rs.getResults());
                    Logger.info(rs.getResults());
                    JsonArray jsonArray = rs.getResults().get(0);
                    Logger.info(rs.getColumnNames());
                    Logger.info(jsonArray);

                    if (jsonArray.getString(1) != null) {
                        reponse.put("FactionNr", jsonArray.getInteger(0));
                        reponse.put("FactionName", jsonArray.getString(1));
                        reponse.put("ClanNr", jsonArray.getInteger(2));
                        reponse.put("ClanName", jsonArray.getString(3));
                    }
                    Logger.info("result    !!! " + reponse);
                } else {
                    Logger.warn(lines.getDbInfo(), res.cause());
                }

                eb.send("tetris-21.socket.gameStart.faction.get", reponse);
            });
    }

    public void checkPassword(String username, String password, EventBus eb) {
        JsonObject passwordFromDb = new JsonObject();
        JsonObject passwordResult = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(GETPASSWORD, params[0], res -> {
            if (res.succeeded()) {
                ResultSet rs = res.result();
                if (rs.getResults().size() == 0) {
                    passwordResult.put(lines.getCanLogin(), lines.getFalse());
                } else {
                    passwordFromDb.put(lines.getPassword(), rs.getResults().get(0).getString(0));
                    boolean samePassword = BCrypt.checkpw(password, passwordFromDb.getString(lines.getPassword()));
                    passwordResult.put(lines.getCanLogin(), String.valueOf(samePassword));
                }
            } else {
                Logger.warn(lines.getDbInfo(), res.cause());
                passwordResult.put(lines.getCanLogin(), lines.getFalse());
            }
            eb.send("tetris-21.socket.login.server", passwordResult.getString(lines.getCanLogin()));
        });
    }

    public void makeUser(String username, String email, String password, EventBus eb) {
        JsonObject couldLogin = new JsonObject();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        final JsonArray[] params = {new JsonArray()
            .add(username)
            .add(email)
            .add(hashedPassword)
            .add(0)};
        jdbcClient.queryWithParams(makeLogin, params[0], res -> {
            if (res.succeeded()) {
                couldLogin.put(lines.getRegister(), lines.getTrue());
            } else {
                couldLogin.put(lines.getRegister(), lines.getFalse());
                Logger.warn("Could not make login: ", res.cause());
            }
            eb.send("tetris-21.socket.login.make.server", couldLogin.getString(lines.getRegister()));
        });
    }

    public void makePlayer(String playername) {
        final JsonArray[] params = {new JsonArray().add(playername)};
        jdbcClient.queryWithParams(makePlayerName, params[0], res -> {
            if (res.succeeded()) {
                Logger.info("Player was made.");
            } else {
                Logger.warn("Could not make player.");
            }
        });
    }

    public void checkUsername(String username, EventBus eb) {
        JsonObject loginExists = new JsonObject();
        final JsonArray[] params = {new JsonArray()
            .add(username)};
        jdbcClient.queryWithParams(USEREXISTS, params[0], res -> {
            if (res.succeeded()) {
                ResultSet rs = res.result();
                if (rs.getResults().size() == 0) {
                    loginExists.put(lines.getUsername(), lines.getFalse());
                } else {
                    loginExists.put(lines.getUsername(), lines.getTrue());
                }
            } else {
                Logger.warn("Could not check username: ", res.cause());
            }
            eb.send("tetris-21.socket.login.username.server", loginExists.getString(lines.getUsername()));
        });
    }


    //    public boolean chooseFaction(String playerName, String clanName) {
    //        final boolean[] happened = new boolean[1];
    //        JsonArray params = new JsonArray().add(getClanNr(clanName)).add(getUserid(playerName));
    //        jdbcClient.updateWithParams(chooseFaction, params,res -> {
    //            if (res.succeeded()) {
    //                UpdateResult updateResult = res.result();
    //                if (updateResult.getUpdated() > 0) {
    //                    happened[0] =lines.getFalse(
    //                    Logger.warn("Could not insert into faction.");
    //                } else {
    //                    happened[0] = true;
    //                }
    //            } else {
    //                happened[0] =lines.getFalse(
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
    //                    userId[0] = row.getString(lines.getUserid();
    //                }
    //            }
    //        });
    //        return userId[0];
    //    }
    //
    //    private String getClanNr(String clanName) {
    //        final String[] clanNr = new String[1];
    //        JsonArray params = new JsonArray().add(clanName);
    //        jdbcClient.queryWithParams(GETCLAN, params, res -> {
    //            ResultSet rs = res.result();
    //            List<JsonObject> rows = rs.getRows();
    //            for (JsonObject row : rows) {
    //                clanNr[0] = row.getString("clanNr");
    //            }
    //        });
    //        return clanNr[0];
    //    }


}
