package game;

import game.events.Events;
import game.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * @author      Remote Access Tetris aka RAT
 */
public class Game {

    private List<Player> players;
    private Events events;

    public Game(final Events events) {
        players = new ArrayList<>();
        this.events = events;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(final List<Player> players) {
        this.players = players;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(final Events events) {
        this.events = events;
    }

    public void addPlayer(final Player player) {
        players.add(player);
    }

    public void removePlayer(final Player player) {
        players.remove(player);
    }

    @Override
    public String toString() {
        return "Game{"
                + "players=" + players
                + ", events=" + events + '}';
    }
}
