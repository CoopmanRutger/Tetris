package game;

import game.events.Events;
import game.player.Player;
import game.player.playfield.block.Block;
import game.player.playfield.block.TypesOfBlocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author      Remote Access Tetris aka RAT
 */
public class Game {

    private List<Player> players;
    private Events events;
    public Game(Events events) {
        players = new ArrayList<>();
        this.events = events;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Events getEvents() {
        return events;
    }

    public void setEvents(Events events) {
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
                ", players=" + players +
                ", events=" + events +
                '}';
    }
}
