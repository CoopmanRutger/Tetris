package game.api.jdbcinteractor;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;
import org.pmw.tinylog.Logger;
import java.time.Instant;
import java.util.List;

public class ConsumerHandlers {

    private final String GET_FACTION = "SELECT * FROM faction " +
            "JOIN clan_faction ON faction.factionNr = clan_faction.factionNr" +
            "JOIN clan_user ON clan_faction.clanNr = clan_user.clanNr" +
            "WHERE clan_user.userId = (SELECT userId FROM users WHERE playerName = ?)";
    private final String CHOOSE_FACTION = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";
    private final String GET_USER = "SELECT * FROM player WHERE playerName = ?";
    private final String GET_CLAN = "SELECT * FROM clans WHERE name = ?";

    private JDBCClient jdbcClient;

    public ConsumerHandlers(JDBCClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    public <T> void storeMessage(final Message message) {
        Instant name = Instant.now();
        jdbcClient.updateWithParams(
                "INSERT INTO heroes(name) VALUES (?)",
                new JsonArray().add(((JsonObject) message.body()).getString("name")),
//                        .add(((JsonObject) message.body()).getString("user"))
//                        .add(((JsonObject) message.body()).getString("content")),
                res -> {
                    if (res.succeeded()) {
                        res.result();
                        Logger.info("inserted: {} ", res.result().toJson());
                    } else {
                        Logger.warn("Could not write message to db: {} ", res.cause());
                    }
                });
    }

    public void chooseFaction(String playerName, String clanName) {
        JsonArray params = new JsonArray().add(getClanNr(clanName)).add(getUserid(playerName));
        jdbcClient.updateWithParams(CHOOSE_FACTION, params,res -> {
            if (res.succeeded()) {
                UpdateResult updateResult = res.result();
                if (updateResult.getUpdated() > 0) {
                    Logger.warn("Could not insert into faction.");
                }
            } else {
                Logger.warn("Could not insert into faction.", res.cause());
            }
        });
    }

    public String getFaction(String playerName) {
        final String[] factionName = new String[1];
        JsonArray params = new JsonArray().add(playerName);
        jdbcClient.queryWithParams(GET_FACTION, params,res -> {
            if (res.succeeded()) {
                ResultSet rs = res.result();
                List<JsonObject> rows = rs.getRows();
                if (rows.size() > 0) {
                    for (JsonObject row : rows) {
                        factionName[0] = row.getString("name");
                    }
                } else {
                    factionName[0] = null;
                }
            } else {
                Logger.warn("Could not get info from DB: ", res.cause());
            }
        });
        return factionName[0];
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
