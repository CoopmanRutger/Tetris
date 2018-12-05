package game.api.jdbcinteractor;

import game.api.webapi.GameController;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import org.pmw.tinylog.Logger;

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

                        System.out.println("result" + rs.getResults().get(0));
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
                        JsonArray jsonArray = rs.getResults().get(0);
                        System.out.println(jsonArray);

                        if (jsonArray.getString(1) != null) {
                            reponse.put("factionName",jsonArray.getString(1));
                            reponse.put("clanNr",jsonArray.getInteger(2));
                            reponse.put("clanName",jsonArray.getString(3));
                            reponse.put("factionNr",jsonArray.getInteger(4));
                            reponse.put("userNr",jsonArray.getInteger(5));
                        } else {
                            reponse.put("factionName","none");
                        }
                        System.out.println("result!!! " + reponse);
                    } else {
                    Logger.warn("Could not get info from DB: ", res.cause());
                    }

                    eb.send("tetris-21.socket.faction.get", reponse);
                });
    }


//
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
