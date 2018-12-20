package game.events.event;

import game.player.Player;
import game.player.playfield.Playfield;

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
    public String getName() {
        return "Tornado";
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void activate() {
        activateOnTime();
    }

    private void activateOnTime() {
        List<List<Integer>> playfieldUpdatedVersion = new ArrayList<>();
        List<List<Integer>> CurrentPlayfield = playfield.getPlayfield();

        for (List<Integer> playfieldLine : CurrentPlayfield) {
            Collections.shuffle(playfieldLine);
        }

        List<List<Integer>> lowestLines = new ArrayList<>();
        List<List<Integer>> highestLines = new ArrayList<>();

        for (int i = CurrentPlayfield.size() - 1; i >= 7; i--) {
            lowestLines.add(CurrentPlayfield.get(i));
        }

        for (int i = 6; i >= 0; i--) {
            highestLines.add(CurrentPlayfield.get(i));
        }

        Collections.shuffle(lowestLines);

        playfieldUpdatedVersion.addAll(highestLines);
        playfieldUpdatedVersion.addAll(lowestLines);

        playfield.setPlayfield(playfieldUpdatedVersion);
    }

    @Override
    public String toString() {
        return "Tornado{" +
                "playfield=" + playfield +
                '}';
    }
}
