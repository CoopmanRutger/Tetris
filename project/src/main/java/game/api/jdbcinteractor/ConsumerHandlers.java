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
 * @author      Remote Access Tetris aka RAT
 */
public class ConsumerHandlers {

    private JDBCClient jdbcClient;
    private GameController controller;

        private final String GET_BASIC = "SELECT * FROM factions " +
                "Left join clans on factions.factionnr = clans.factionnr " +
                "Left join factions_users ON factions.factionNr= factions_users.factionNr " +
                "Left Join users on factions_users.userid = users.userid " +
                " WHERE users.userid = ? ";

    private final String CHOOSE_FACTION = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";

    private final String GET_USER = "select * from users " +
                "left join players on users.userid = players.userid " +
                "WHERE username = ?";
    private final String MAKE_USER = "INSERT INTO USERS (Username, email) VALUES ( ?, null);";
    private final String GET_CLAN = "SELECT * FROM clans WHERE name = ?";
    private final String GET_PASSWORD = "SELECT password FROM users WHERE username = ?";
    private final String MAKE_LOGIN = "INSERT INTO users (username, email, password, gold) " +
                                        "VALUES (?, ?, ?, ?)";

    private  final String MAKE_FACTION = "UPDATE FACTIONS_USERS " +
                "SET Factionnr = ?, userid= ? WHERE userid = ?";

    private final  String GET_GOLD = "SELECT gold FROM USERS " +
                "WHERE Userid = ?";

    private final  String MAKE_RANDOM_FACTION = "insert into FACTIONS_USERS  ( factionnr, userid) " +
                "values( 5, ?)";
    private final String USEREXISTS = "select * from users where username = ?";
    private final String MAKEPLAYERNAME = "INSERT INTO players (userid, PLAYERNAME, xp, level) VALUES ((select max(userid) from users), ?, 0 , 1)";




    public ConsumerHandlers() {
        this(null);
    }

    public ConsumerHandlers(GameController controller) {
        jdbcClient = ConnectionDatabase.getJdbcClient();
        this.controller = controller;
    }


    public void getPlayerInfo(String username, EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(GET_USER, params[0],
                res -> {
                    JsonObject player = new JsonObject();
                    if (res.succeeded()) {
                        ResultSet rs = res.result();
                        player.put("userId", rs.getResults().get(0).getInteger(0));
                        player.put("username", rs.getResults().get(0).getString(1));
                        player.put("playerId", rs.getResults().get(0).getInteger(5));
                        player.put("playerName", rs.getResults().get(0).getString(6));
                        System.out.println(player);
                        insertRandomFaction(rs.getResults().get(0).getInteger(0));

                    } else {
                        Logger.warn("Could not get info from DB: ", res.cause());
                    }
                    controller.setPlayer1(player.getString("playerName"));
                    controller.setUsername1(player.getString("username"));

                    eb.send("tetris-21.socket.homescreen.playerinfo", player.encode());
                });
    }

    private void insertRandomFaction(int userId) {
        JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(userId)};
        jdbcClient.queryWithParams(MAKE_RANDOM_FACTION, params[0],
                res -> {
                    if (res.succeeded()) {
                        System.out.println("check");

                    } else {
                        Logger.warn("Could not get info from DB: ", res.cause());
                    }
                });
    }

    public void getGold(int UserId, EventBus eb) {
        JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(UserId)};
        jdbcClient.queryWithParams(GET_GOLD, params[0],
                res -> {
                    if (res.succeeded()) {
                        ResultSet rs = res.result();
                        reponse.put("gold",rs.getResults().get(0) );
                    } else {
                        Logger.warn("Could not get info from DB: ", res.cause());
                    }
                    eb.send("tetris-21.socket.gold.get", reponse);
                });
    }


    public void insertFaction(int factionNr, int userId, EventBus eb) {
        final boolean[] happened = new boolean[1];
        JsonArray params = new JsonArray().add(factionNr).add(userId).add(userId);
        jdbcClient.updateWithParams(MAKE_FACTION, params,res -> {
            if (res.succeeded()) {
                UpdateResult updateResult = res.result();
                if (updateResult.getUpdated() > 0) {
                    happened[0] = false;
                    Logger.warn("Could not insert into faction.");
                } else {
                    happened[0] = true;
                }
            } else {
                happened[0] = false;
                Logger.warn("Could not insert into faction.", res.cause());
            }
        });
    }




    private void makeNewplayerUsername(String username, EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(MAKE_USER, params[0],
                res -> {
                    JsonObject player = new JsonObject();
                    if (res.succeeded()) {
                        ResultSet rs = res.result();
                        player.put("player", rs.getResults().get(0));
                        System.out.println(rs.getResults().get(0).getString(1));

                        System.out.println("result!!! " + player);
                    } else {
                        Logger.warn("Could not get info from DB: ", res.cause());
                    }

                    controller.setUsername1(player.getJsonArray("player").getString(1));
                    eb.send("tetris-21.socket.homescreen.playerinfo", player.encode());
                });
    }


    public void getBasic(int playerId, EventBus eb) {
        JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playerId)};
        jdbcClient.queryWithParams(GET_BASIC, params[0],
                res -> {
                    if (res.succeeded()) {
                        ResultSet rs = res.result();
                        System.out.println("rs: " + rs.getResults());
                        System.out.println(rs.getResults());
                        JsonArray jsonArray = rs.getResults().get(0);
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
                    Logger.warn("Could not get info from DB: ", res.cause());
                    }

                    eb.send("tetris-21.socket.gameStart.faction.get", reponse);
                });
    }

    public void checkPassword(String username, String password, EventBus eb) {
        JsonObject passwordFromDb = new JsonObject();
        JsonObject passwordResult = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(GET_PASSWORD, params[0], res -> {
            if (res.succeeded()) {
                ResultSet rs = res.result();
                passwordFromDb.put("password", rs.getResults().get(0).getString(0));
                boolean samePassword = BCrypt.checkpw(password, passwordFromDb.getString("password"));
                passwordResult.put("canLogin", "" + samePassword);
            } else {
                Logger.warn("Could not get info from DB: ", res.cause());
                passwordResult.put("canLogin", "false");
            }
            eb.send("tetris-21.socket.login.server", passwordResult.getString("canLogin"));
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
        jdbcClient.queryWithParams(MAKE_LOGIN, params[0], res -> {
            if (res.succeeded()) {
                couldLogin.put("register", "true");
            } else {
                couldLogin.put("register", "false");
                Logger.warn("Could not make login: ", res.cause());
            }
            eb.send("tetris-21.socket.login.make.server", couldLogin.getString("register"));
        });
    }

    public void makePlayer(String playername) {
        JsonObject couldMakePlayername = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playername)};
        jdbcClient.queryWithParams(MAKEPLAYERNAME, params[0], res -> {
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
                    loginExists.put("username", "false");
                } else {
                    loginExists.put("username", "true");
                }
            } else {
                Logger.warn("Could not check username: ", res.cause());
            }
            eb.send("tetris-21.socket.login.username.server", loginExists.getString("username"));
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
//        jdbcClient.queryWithParams(GET_USER, params, res -> {
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
//        jdbcClient.queryWithParams(GET_CLAN, params, res -> {
//            ResultSet rs = res.result();
//            List<JsonObject> rows = rs.getRows();
//            for (JsonObject row : rows) {
//                clanNr[0] = row.getString("clanNr");
//            }
//        });
//        return clanNr[0];
//    }


}
