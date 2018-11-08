package game.api;

import game.Game;
import game.api.webapi.Consumers;
import game.api.webapi.Routes;
import game.events.Events;
import game.player.Player;
import io.vertx.core.json.JsonObject;

public class Start {
    public static void main(String... args) throws InterruptedException {

        Routes routes = new Routes();
        routes.homeScreen();

        Events events = new Events();

        Player player = new Player("rutger");
        Player player1 = new Player("jan");
        Game game = new Game(events);
        game.addPlayer(player);
        game.addPlayer(player1);
        routes.BattleFieldStart();

        routes.sendBlockOneByOne(game);



    }
}

