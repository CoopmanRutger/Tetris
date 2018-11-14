package game.player.playfields;

import game.player.playfields.playfield.Playfield;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Playfields {

    private List<Playfield> playfields;

    public Playfields() {
        playfields = new ArrayList<>();
        addPlayfield(new Playfield());
    }

    public List<Playfield> getPlayfields() {
        return playfields;
    }

    public void addPlayfield(Playfield playfield){
        playfields.add(playfield);
    }

    public void removePlayfield(Playfield playfield){
        playfields.remove(playfield);
    }

    @Override
    public String toString() {
        return "Playfields{"
                + playfields
                + '}';
    }
}
