package tetris.events.event;

import tetris.player.playfield.Playfield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tornado implements Event {


    private Trigger trigger;
    private Playfield playfield;

    public Tornado(Trigger trigger, Playfield playfield) {
        this.trigger = trigger;
        this.playfield = playfield;
    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public void activate() {
        activateOnTime();
    }

    private void activateOnTime() {
        List<List<Integer>> playfieldUpdatedVersion = new ArrayList<>();
        List<List<Integer>> currentPlayfield = playfield.getPlayfield();

        for (List<Integer> playfieldLine : currentPlayfield) {
            Collections.shuffle(playfieldLine);
        }

        List<List<Integer>> lowestLines = new ArrayList<>();
        List<List<Integer>> highestLines = new ArrayList<>();


        int number = 10;
        for (int i = currentPlayfield.size() - 1; i >= number; i--) {
            lowestLines.add(currentPlayfield.get(i));
        }

        for (int i = number - 1; i >= 0; i--) {
            highestLines.add(currentPlayfield.get(i));
        }

        Collections.shuffle(lowestLines);

        playfieldUpdatedVersion.addAll(highestLines);
        playfieldUpdatedVersion.addAll(lowestLines);

        playfield.setPlayfield(playfieldUpdatedVersion);

        for (int i = 0; i < number - 2; i++) {
            for (int j = 0; j < playfield.getPlayfield().get(i).size(); j++) {
                playfield.getPlayfield().get(i).set(j, 0);
            }
        }
    }

    @Override
    public String toString() {
        return "Tornado{"
            + "playfield=" + playfield
            + '}';
    }
}
