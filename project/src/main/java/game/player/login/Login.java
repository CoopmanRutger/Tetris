package game.player.login;

import game.api.jdbcinteractor.Database;
import org.mindrot.jbcrypt.BCrypt;

public class Login {

    public boolean checkLogin(String username, String password) {
        String passwordFromDb = Database.getDB().getConsumerHandler().getPasswordFor(username);
        if (passwordFromDb != null) {
            return BCrypt.checkpw(password, passwordFromDb);
        } else {
            return false;
        }
    }

    public void makeLogin(String username, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(15));
        Database.getDB().getConsumerHandler().makeUser(username, email, hashedPassword);
    }
}
