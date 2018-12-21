package tetris.api.jdbcinteractor;

public class StringLines {
    private static final String USERNAME = "username";
    private static final String PLAYER = "player";
    private static final String FALSE = "false";
    private static final String USERSID = "userId";
    private static final String DBINFO = "Could not get info from DB: ";
    private static final String SOCKETPLAYERINFO = "tetris-21.socket.homescreen.playerinfo";
    private static final String CANLOGIN = "canLogin";
    private static final String PASSWORD = "password";
    private static final String REGISTER = "register";
    private static final String TRUE = "true";

    public String getUsername() {
        return USERNAME;
    }

    public String getPlayer() {
        return PLAYER;
    }

    public String getFalse() {
        return FALSE;
    }

    public String getUserid() {
        return USERSID;
    }

    public String getDbInfo() {
        return DBINFO;
    }

    public String getSocketPlayerInfo() {
        return SOCKETPLAYERINFO;
    }

    public String getCanLogin() {
        return CANLOGIN;
    }

    public String getPassword() {
        return PASSWORD;
    }

    public String getRegister() {
        return REGISTER;
    }

    public String getTrue() {
        return TRUE;
    }
}
