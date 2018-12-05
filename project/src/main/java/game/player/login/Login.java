package game.player.login;

import game.api.jdbcinteractor.Database;
import org.mindrot.jbcrypt.BCrypt;

public class Login {

    public boolean checkLogin(String username, String password) {
        String passwordFromDb = Database.getDB().getConsumerHandlers().getPasswordFor(username);
        if (passwordFromDb != null) {
            return BCrypt.checkpw(password, passwordFromDb);
        } else {
            return false;
        }
    }

    public Boolean makeLogin(String username, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String canLogin = Database.getDB().getConsumerHandlers().makeUser(username, email, hashedPassword);
        return canLogin.equals("true");
    }
}
