package game.api.jdbcinteractor;

/**
 * @author Remote Access Tetris aka RAT
 */

public class InfoStrings {

    private static final String USERNAMESTRING = "username";
    private static final String PLAYERINFOSOCKERSTRING = "tetris-21.socket.homescreen.playerinfo";
    private static final String DBINFOSTRING = "Could not get info from DB: ";
    private static final String PLAYERSTRING = "player";
    private static final String PASSWORDSTRING = "password";
    private static final String CANLOGINSTRING = "canLogin";
    private static final String FALSESTRING = "false";
    private static final String TRUESTRING = "true";
    private static final String REGISTERSTRING = "register";

    public String getUsernameString() {
        return USERNAMESTRING;
    }

    public String getPlayerInfoSockerString() {
        return PLAYERINFOSOCKERSTRING;
    }

    public String getDbInfoString() {
        return DBINFOSTRING;
    }

    public String getPlayerString() {
        return PLAYERSTRING;
    }

    public String getPasswordString() {
        return PASSWORDSTRING;
    }

    public String getCanLoginString() {
        return CANLOGINSTRING;
    }

    public String getFalseString() {
        return FALSESTRING;
    }

    public String getTrueString() {
        return TRUESTRING;
    }

    public String getRegisterString() {
        return REGISTERSTRING;
    }
}
