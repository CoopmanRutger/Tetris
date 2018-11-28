package game.api.jdbcinteractor;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import org.pmw.tinylog.Logger;

import java.time.Instant;
import java.util.List;

public class ConsumerHandlers {

    private JDBCClient jdbcClient;

    private final String GET_FACTION = "SELECT * FROM faction " +
            "Left JOIN clans ON faction.factionNr = clans.factionNr " +
            "Left JOIN clan_users ON clans.clanNr = clan_users.clanNr " +
            "WHERE clan_users.usernr = (SELECT users.userId FROM users " +
                "left join player on users.userid = player.userid WHERE playerName = ?)";
    private final String CHOOSE_FACTION = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";
    private final String GET_USER = "SELECT * FROM player WHERE playerName = ?";
    private final String GET_CLAN = "SELECT * FROM clans WHERE name = ?";




    public ConsumerHandlers() {
        jdbcClient = ConnectionDatabase.jdbcClient;
    }




    public boolean chooseFaction(String playerName, String clanName) {
        final boolean[] happened = new boolean[1];
        JsonArray params = new JsonArray().add(getClanNr(clanName)).add(getUserid(playerName));
        jdbcClient.updateWithParams(CHOOSE_FACTION, params,res -> {
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

        return happened[0];
    }

    public String getFaction(String playerName) {
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
                });
        System.out.println("result  " + reponse);
        return reponse.getString("faction");
    }


    private String getUserid(String playerName) {
        final String[] userId = new String[1];
        JsonArray params = new JsonArray().add(playerName);
        jdbcClient.queryWithParams(GET_USER, params, res -> {
            if (res.succeeded()) {
                ResultSet rs = res.result();
                List<JsonObject> rows = rs.getRows();
                for (JsonObject row : rows) {
                    userId[0] = row.getString("userId");
                }
            }
        });
        return userId[0];
    }

    private String getClanNr(String clanName) {
        final String[] clanNr = new String[1];
        JsonArray params = new JsonArray().add(clanName);
        jdbcClient.queryWithParams(GET_CLAN, params, res -> {
            ResultSet rs = res.result();
            List<JsonObject> rows = rs.getRows();
            for (JsonObject row : rows) {
                clanNr[0] = row.getString("clanNr");
            }
        });
        return clanNr[0];
    }


}
