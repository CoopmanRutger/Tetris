package game.player.playfields;

import game.player.playfields.playfield.Playfield;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Playfields {

    private final List<Playfield> playfieldsList;

    public Playfields() {
        playfieldsList = new ArrayList<>();
        addPlayfield(new Playfield());
    }

    public List<Playfield> getPlayfields() {
        return playfieldsList;
    }

    public void addPlayfield(final Playfield playfield) {
        playfieldsList.add(playfield);
    }

    public void removePlayfield(final Playfield playfield) {
        playfieldsList.remove(playfield);
    }

    @Override
    public String toString() {
        return "Playfields{"
            + playfieldsList
            + '}';
    }
}
