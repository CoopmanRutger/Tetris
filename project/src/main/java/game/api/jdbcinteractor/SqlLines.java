package game.api.jdbcinteractor;

/**
 * @author Remote Access Tetris aka RAT
 */

public class SqlLines {

    private static final String CHOOSEFACTION = "INSERT INTO clan_user(clanNr, userId) VALUES (?, ?)";
    private static final String USEREXISTS = "select * from users where username = ?";
    private static final String MAKEUSER = "INSERT INTO USERS (Username, email) VALUES ( ?, null);";
    private static final String CLAN = "SELECT * FROM clans WHERE name = ?";
    private static final String PASSWORD = "SELECT password FROM users WHERE username = ?";
    private static final String BASIC = "SELECT * FROM factions "
        + "Left join clans on factions.factionnr = clans.factionnr "
        + "Left join factions_users ON factions.factionNr= factions_users.factionNr "
        + "Left Join users on factions_users.userid = users.userid "
        + " WHERE users.userid = ? ";

    private static final String USER = "select * from users "
        + "left join players on users.userid = players.userid "
        + "WHERE username = ?";

    private final String makeLogin = "INSERT INTO users (username, email, password, gold) "
        + "VALUES (?, ?, ?, ?)";

    private final String makeFaction = "UPDATE FACTIONS_USERS "
        + "SET Factionnr = ?, userid= ? WHERE userid = ?";

    private final String gold = "SELECT gold FROM USERS "
        + "WHERE Userid = ?";

    private final String makeRandomFaction = "insert into FACTIONS_USERS  ( factionnr, userid) "
        + "values( 5, ?)";

    private final String makePlayerName = "INSERT INTO players (userid, PLAYERNAME, xp, level) VALUES"
        + "((select max(userid) from users), ?, 0 , 1)";

    public String getBasic() {
        return BASIC;
    }

    public String getChooseFaction() {
        return CHOOSEFACTION;
    }

    public String getUser() {
        return USER;
    }

    public String getMakeUser() {
        return MAKEUSER;
    }

    public String getClan() {
        return CLAN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getMakeLogin() {
        return makeLogin;
    }

    public String getMakeFaction() {
        return makeFaction;
    }

    public String getGold() {
        return gold;
    }

    public String getMakeRandomFaction() {
        return makeRandomFaction;
    }

    public String getUserExists() {
        return USEREXISTS;
    }

    public String getMakePlayerName() {
        return makePlayerName;
    }
}
