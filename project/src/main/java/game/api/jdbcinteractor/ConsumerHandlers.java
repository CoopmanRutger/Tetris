package game.api.jdbcinteractor;

import game.api.webapi.GameController;
import game.events.event.Event;
import game.player.login.Login;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;
import org.pmw.tinylog.Logger;
import sun.rmi.runtime.Log;

public class ConsumerHandlers {

    private JDBCClient jdbcClient;
    private GameController controller;

    private final String GET_FACTION = "SELECT * FROM faction " +
            "Left JOIN clans ON faction.factionNr = clans.factionNr " +
            "Left JOIN clan_users ON clans.clanNr = clan_users.clanNr " +
            "WHERE clan_users.usernr = (SELECT users.userId FROM users " +
                "left join player on users.userid = player.userid WHERE playerName = ?)";
    private final String CHOOSE_FACTION = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";
    private final String GET_USER = "select * from users " +
                "left join player on users.userid = player.userid " +
                "WHERE username = ?";
    private final String MAKE_USER = "INSERT INTO USERS (Username, email) VALUES ( ?, null);";
    private final String GET_CLAN = "SELECT * FROM clans WHERE name = ?";
    private final String GET_PASSWORD = "SELECT password FROM users WHERE username = ?";
    private final String MAKE_LOGIN = "INSERT INTO users (username, email, password) " +
                                        "VALUES (?, ?, ?)";

    public ConsumerHandlers() {
        this(null);
    }

    public ConsumerHandlers(GameController controller) {
        jdbcClient = ConnectionDatabase.jdbcClient;
        this.controller = controller;
    }


    public void getPlayerInfo(String username, EventBus eb) {
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(GET_USER, params[0],
                res -> {
                    JsonObject player = new JsonObject();
                    if (res.succeeded()) {
                        ResultSet rs = res.result();

                        System.out.println(rs.getResults().get(0));
                        player.put("playerId", rs.getResults().get(0).getInteger(0));
                        player.put("username", rs.getResults().get(0).getString(1));
                        player.put("email", rs.getResults().get(0).getString(2));
//                        player.put("playerid", rs.getResults().get(0).getInteger(3));
                        player.put("playerName", rs.getResults().get(0).getString(4));
                        player.put("heroNr", rs.getResults().get(0).getInteger(5));
                        player.put("heroExp", rs.getResults().get(0).getInteger(6));
                        player.put("heroLvl", rs.getResults().get(0).getInteger(7));

                    } else {
                        Logger.warn("Could not get info from DB: ", res.cause());
                    }
//                    if (player.getJsonArray("player").getString(1) == null){
//                        makeNewplayerUsername(username, eb);)

                    controller.setPlayer1(player.getString("playerName"));
                    controller.setUsername1(player.getString("username"));
                    eb.send("tetris-21.socket.homescreen.playerinfo", player.encode());
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


    public void getFaction(String playerName, EventBus eb) {
        JsonObject reponse = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(playerName)};
        jdbcClient.queryWithParams(GET_FACTION, params[0],
                res -> {
                    if (res.succeeded()) {
                        ResultSet rs = res.result();
                        System.out.println("rs: " + rs.getResults());
                        reponse.put("faction", rs.getResults().get(0));
                        JsonArray jsonArray = rs.getResults().get(0);

                        if (jsonArray.getString(1) != null) {
                            reponse.put("factionName",jsonArray.getString(1));
                            System.out.println(jsonArray.getString(1));
                        } else {
                            reponse.put("factionName","none");
                        }
                        System.out.println("result!!! " + reponse);
                    } else {
                    Logger.warn("Could not get info from DB: ", res.cause());
                    }

                    eb.send("tetris-21.socket.faction.get", reponse.getString("factionName"));
                });
    }

    public void getPasswordFor(Login login, String username, EventBus eb) {
        JsonObject password = new JsonObject();
        final JsonArray[] params = {new JsonArray().add(username)};
        jdbcClient.queryWithParams(GET_PASSWORD, params[0], res -> {
            if (res.succeeded()) {
                ResultSet rs = res.result();
                password.put("password", rs.getResults().get(0).getString(0));
            } else {
                Logger.warn("Could not get info from DB: ", res.cause());
            }
            eb.send("tetris-21.socket.login.server", password.getString("password"));
            //login.setPassword(password.getString("password"));
        });
    }

    public void makeUser(String username, String email, String password, EventBus eb) {
        JsonObject couldLogin = new JsonObject();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        final JsonArray[] params = {new JsonArray()
                .add(username)
                .add(email)
                .add(hashedPassword)};
        jdbcClient.queryWithParams(MAKE_LOGIN, params[0], res -> {
            if (res.succeeded()) {
                couldLogin.put("register", "true");
            } else {
                couldLogin.put("register", "false");
                Logger.warn("Could not make login: ", res.cause());
            }
            eb.send("tetris-21.socket.login.make.server", couldLogin.getString("register"));
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
