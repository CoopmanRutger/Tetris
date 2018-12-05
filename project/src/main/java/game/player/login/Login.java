package game.player.login;

import game.api.jdbcinteractor.ConsumerHandlers;
import game.api.jdbcinteractor.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.TimeUnit;

public class Login {

    private String canLogin;
    private String passwordFromDb;

    public boolean checkLogin(String username, String password) {
        new ConsumerHandlers().getPasswordFor(this, username);
        if (passwordFromDb != null) {
            return BCrypt.checkpw(password, passwordFromDb);
        } else {
            return false;
        }
    }

    public String makeLogin(String username, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        new ConsumerHandlers().makeUser(this, username, email, hashedPassword);
        return getCanLogin();
    }

    public void mayLogin(String login) {
        System.out.println("login: " + login);
        canLogin = login;
    }

    public void setPassword(String password) {
        passwordFromDb = password;
    }

    public String getCanLogin() {
        return canLogin;
    }

    public String getPasswordFromDb() {
        return passwordFromDb;
    }
}
