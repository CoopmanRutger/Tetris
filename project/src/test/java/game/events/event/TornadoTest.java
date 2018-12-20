package game.events.event;

import game.player.Player;
import game.player.playfield.Playfield;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TornadoTest {

    @Test
    public void activate() {
        Player player = new Player("Rutger123");
        Event tornado = new Tornado("tornado", Trigger.TIME, player);

        List<List<Integer>> playfield = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            List<Integer> playWidth = new ArrayList<>();
            int z = 0;
            for (int j = 0; j < 12; j++) {
                if (j == z*2) {
                    playWidth.add(1);
                    z++;
                } else {
                    playWidth.add(0);
                }
            }
            playfield.add(playWidth);
        }

        Playfield playerField = new Playfield(playfield);
        player.addPlayfield("Rutger123", playerField);

        tornado.activate();

        assertNotSame((playerField.getPlayfield()), player.getPlayfieldByName(player.getName()).getPlayfield());
    }
}