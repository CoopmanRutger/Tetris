package game.player.login;

import game.api.jdbcinteractor.ConsumerHandlers;
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

    public Boolean makeLogin(String username, String email, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String canLogin = new ConsumerHandlers().makeUser(username, email, hashedPassword);
        //String canLogin = Database.getDB().getConsumerHandler().makeUser(username, email, hashedPassword);
        if (canLogin == null){
            return false;
        } else {
            return canLogin.equals("true");
        }
    }
}
