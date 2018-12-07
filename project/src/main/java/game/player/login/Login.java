package game.player.login;

import game.api.jdbcinteractor.ConsumerHandlers;
import game.api.jdbcinteractor.Database;
import game.events.event.Event;
import io.vertx.core.eventbus.EventBus;
import org.mindrot.jbcrypt.BCrypt;

public class Login {

    public boolean checkLogin(String password, String passwordFromDb) {
        if (passwordFromDb != null) {
            return BCrypt.checkpw(password, passwordFromDb);
        } else {
            return false;
        }
    }

    public void getPasswordFromDb(String username, EventBus eb) {
        //new ConsumerHandlers().getPasswordFor(this, username, eb);
    }

    public void makeLogin(String username, String email, String password, EventBus eb) {
        System.out.println(password);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        //new ConsumerHandlers().makeUser(this, username, email, hashedPassword, eb);
    }
}
