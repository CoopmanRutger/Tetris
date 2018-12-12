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
    private GameController controller;

    private final String getBasic = "SELECT * FROM factions "
        + "Left join clans on factions.factionnr = clans.factionnr "
        + "Left join factions_users ON factions.factionNr= factions_users.factionNr "
        + "Left Join users on factions_users.userid = users.userid "
        + " WHERE users.userid = ? ";

    private final String chooseFaction = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";

    private final String getUser = "select * from users "
        + "left join players on users.userid = players.userid "
        + "WHERE username = ?";
    private final String makeUser = "INSERT INTO USERS (Username, email) VALUES ( ?, null);";
    private final String getClan = "SELECT * FROM clans WHERE name = ?";
    private final String getPassword = "SELECT password FROM users WHERE username = ?";
    private final String makeLogin = "INSERT INTO users (username, email, password, gold) "
        + "VALUES (?, ?, ?, ?)";

    private final String makeFaction = "UPDATE FACTIONS_USERS "
        + "SET Factionnr = ?, userid= ? WHERE userid = ?";

    private final String getGold = "SELECT gold FROM USERS "
        + "WHERE Userid = ?";

    private final String makeRandomFaction = "insert into FACTIONS_USERS  ( factionnr, userid) "
        + "values( 5, ?)";

    private String info1 = "Could not get info from DB: ";
    private String info2 = "Could not insert into faction.";
    private String usernameString = "username";
    private String playerNameString = "playerName";
    private String address = "tetris-21.socket.homescreen.playerinfo";
    private String playerString = "player";
    private String passwordString = "password";
    private String canLoginString = "canLogin";
    private String falseString = "false";
    private String couldLoginString = "couldLogin";
    private String registerString = "register";

    public ConsumerHandlers() {
        this(null);
    }

    public ConsumerHandlers(GameController controller) {
        jdbcClient = ConnectionDatabase.jdbcClient;
        this.controller = controller;
    }


    public void getPlayerInfo(String username, EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(getUser, params[0],
            res -> {
                final JsonObject player = new JsonObject();
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    player.put("userId", rs.getResults().get(0).getInteger(0));
                    player.put(usernameString, rs.getResults().get(0).getString(1));
                    player.put("playerId", rs.getResults().get(0).getInteger(5));
                    player.put(playerNameString, rs.getResults().get(0).getString(6));
                    System.out.println(player);
                    insertRandomFaction(rs.getResults().get(0).getInteger(0));

                } else {
                    Logger.warn(info1, res.cause());
                }
                controller.setPlayer1(player.getString(playerNameString));
                controller.setUsername1(player.getString(usernameString));

                eb.send(address, player.encode());
            });
    }

    private void insertRandomFaction(int userId) {
        final JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(makeRandomFaction, params[0],
            res -> {
                if (res.succeeded()) {
                    System.out.println("check");

                } else {
                    Logger.warn(info1, res.cause());
                }
            });
    }

    public void getGold(int userId, EventBus eb) {
        final JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(getGold, params[0],
            res -> {
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    reponse.put("gold", rs.getResults().get(0));
                } else {
                    Logger.warn(info1, res.cause());
                }
                eb.send("tetris-21.socket.gold.get", reponse);
            });
    }


    public void insertFaction(int factionNr, int userId, EventBus eb) {
        final boolean[] happened = new boolean[1];
        final JsonArray params = new JsonArray().add(factionNr).add(userId).add(userId);
        jdbcClient.updateWithParams(makeFaction, params, res -> {
            if (res.succeeded()) {
                final UpdateResult updateResult = res.result();
                if (updateResult.getUpdated() > 0) {
                    happened[0] = false;
                    Logger.warn(info2);
                } else {
                    happened[0] = true;
                }
            } else {
                happened[0] = false;
                Logger.warn(info2, res.cause());
            }
        });
    }


    private void makeNewplayerUsername(String username, EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(makeUser, params[0],
            res -> {
                final JsonObject player = new JsonObject();
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    player.put(playerString, rs.getResults().get(0));
                    System.out.println(rs.getResults().get(0).getString(1));

                    System.out.println("result!!! " + player);
                } else {
                    Logger.warn(info1, res.cause());
                }

                controller.setUsername1(player.getJsonArray(playerString).getString(1));
                eb.send(address, player.encode());
            });
    }


    public void getBasic(int playerId, EventBus eb) {
        final JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playerId)};
        jdbcClient.queryWithParams(getBasic, params[0],
            res -> {
                if (res.succeeded()) {
                    final ResultSet rs = res.result();
                    System.out.println("rs: " + rs.getResults());
                    System.out.println(rs.getResults());
                    final JsonArray jsonArray = rs.getResults().get(0);
                    System.out.println(rs.getColumnNames());
                    System.out.println(jsonArray);

                    if (jsonArray.getString(1) != null) {
                        reponse.put("FactionNr", jsonArray.getInteger(0));
                        reponse.put("FactionName", jsonArray.getString(1));
                        reponse.put("ClanNr", jsonArray.getInteger(2));
                        reponse.put("ClanName", jsonArray.getString(3));
                    }
                    System.out.println("result    !!! " + reponse);
                } else {
                    Logger.warn(info1, res.cause());
                }

                eb.send("tetris-21.socket.gameStart.faction.get", reponse);
            });
    }

    public void checkPassword(String username, String password, EventBus eb) {
        final JsonObject passwordFromDb = new JsonObject();
        final JsonObject passwordResult = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(getPassword, params[0], res -> {
            if (res.succeeded()) {
                final ResultSet rs = res.result();
                passwordFromDb.put(passwordString, rs.getResults().get(0).getString(0));
                final boolean samePassword = BCrypt.checkpw(password, passwordFromDb.getString(passwordString));
                passwordResult.put(canLoginString, "" + samePassword);
            } else {
                Logger.warn(info1, res.cause());
                passwordResult.put(canLoginString, falseString);
            }
            eb.send("tetris-21.socket.login.server", passwordResult.getString(canLoginString));
            //login.setPassword(password.getString(passwordString));
        });
    }

    public void makeUser(String username, String email, String password, EventBus eb) {
        final JsonObject couldLogin = new JsonObject();
        final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        final JsonArray[] params = {new JsonArray()
            .add(username)
            .add(email)
            .add(hashedPassword)
            .add(0)};
        jdbcClient.queryWithParams(makeLogin, params[0], res -> {
            if (res.succeeded()) {
                couldLogin.put(registerString, "true");
            } else {
                couldLogin.put(registerString, falseString);
                Logger.warn("Could not make login: ", res.cause());
            }
            eb.send("tetris-21.socket.login.make.server", couldLogin.getString(registerString));
            //login.mayLogin(couldLogin.getString("login"));
        });
    }


    //    public boolean chooseFaction(String playerName, String clanName) {
    //        final boolean[] happened = new boolean[1];
    //        JsonArray params = new JsonArray().add(getClanNr(clanName)).add(getUserid(playerName));
    //        jdbcClient.updateWithParams(CHOOSE_FACTION, params,res -> {
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
