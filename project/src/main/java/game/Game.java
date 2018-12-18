package game;

import game.events.Events;
import game.player.Player;
import game.player.playfields.playfield.block.Block;
import game.player.playfields.playfield.block.TypesOfBlocks;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author      Remote Access Tetris aka RAT
 */
public class Game {

    private Set<Block> blocks;

    private List<Player> players;
    private Events events;
    public Game(Events events) {
        blocks = new HashSet<>();
        MakeBlocks();
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

    public Set<Block> getBlocks() {
        return blocks;
    }

    private void MakeBlocks(){
        Block Lblock = new Block("L-blok", TypesOfBlocks.lBlock, Color.red);
        Block LineBlock = new Block("Line-blok", TypesOfBlocks.lineBlock, Color.blue);
        Block Sblok = new Block("Square-blok", TypesOfBlocks.squareBlock, Color.magenta);
        Block Tblok = new Block("T-blok", TypesOfBlocks.tBlock, Color.orange);
        Block Zblok = new Block("Z-blok", TypesOfBlocks.zBlock, Color.yellow);
        Block ILblock= new Block("IL-block", TypesOfBlocks.inverseLBlock, Color.GREEN);
        Block Nblock = new Block("N-blok", TypesOfBlocks.leftNBlock, Color.pink);
        Block specialBock = new Block("SP-blok", TypesOfBlocks.leftNBlock, Color.white);

        Lblock.makeBlock(2,0,1,3);
        LineBlock.makeBlock(0,0,4,0);
        Sblok.makeBlock(0,0,2,2);
        Tblok.makeBlock(0,1,3,1);
        Zblok.makeBlock(0,1,2,2);
        ILblock.makeBlock(0,0,1,3);
        Nblock.makeBlock(1,0,2,2);
        specialBock.makeBlock(1,0,2,4);

        blocks.add(Lblock);
        blocks.add(LineBlock);
        blocks.add(Sblok);
        blocks.add(Tblok);
        blocks.add(Zblok);
        blocks.add(ILblock);
        blocks.add(Nblock);
        blocks.add(specialBock);
    }


    @Override
    public String toString() {
        return "Game{" +
                "blocks=" + blocks +
                ", players=" + players +
                ", events=" + events +
                '}';
    }
}
