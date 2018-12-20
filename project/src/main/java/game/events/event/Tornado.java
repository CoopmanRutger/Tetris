package game.events.event;

import game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tornado implements Event {

    private String name;
    private Trigger trigger;
    private Player player;

    public Tornado(String name, Trigger trigger, Player player) {
        this.name = name;
        this.trigger = trigger;
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void activate() {
        activateOnTime();
    }

    private void activateOnTime() {
        List<List<Integer>> playfieldUpdatedVersion = new ArrayList<>();
        List<List<Integer>> playfield = player.getPlayfieldByName(player.getName()).getPlayfield();

        for (List<Integer> playfieldLine : playfield) {
            Collections.shuffle(playfieldLine);
        }

        List<List<Integer>> lowestLines = new ArrayList<>();
        List<List<Integer>> highestLines = new ArrayList<>();

        for (int i = playfield.size() - 1; i >= 5; i--) {
            lowestLines.add(playfield.get(i));
        }

        for (int i = 4; i >= 0; i--) {
            highestLines.add(playfield.get(i));
        }

        Collections.shuffle(lowestLines);

        playfieldUpdatedVersion.addAll(highestLines);
        playfieldUpdatedVersion.addAll(lowestLines);

        player.getPlayfieldByName(player.getName()).setPlayfield(playfieldUpdatedVersion);
    }
}
