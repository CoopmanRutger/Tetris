package game;

import game.events.Events;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private Events events;


    public Game(Events events) {
        players = new ArrayList<>();
        this.events = events;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", events=" + events +
                '}';
    }
}

//    ctrl + shift + alt + u ==> uml schema maken  dan e of c