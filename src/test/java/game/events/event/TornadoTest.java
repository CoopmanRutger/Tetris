package game.events.event;

import game.player.Player;
import game.player.playfield.Playfield;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TornadoTest {

    private static final String RUTGER123 = "Rutger123";

    @Test
    public void activate() {
        Player player = new Player(RUTGER123);
        List<List<Integer>> playfield = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            List<Integer> playWidth = createNewArrayList();
            int z = 0;
            for (int j = 0; j < 12; j++) {
                if (j == z * 2) {
                    playWidth.add(1);
                    z++;
                } else {
                    playWidth.add(0);
                }
            }
            playfield.add(playWidth);
        }

        Playfield playerField = new Playfield(playfield);
        player.addPlayfield(RUTGER123, playerField);
        Event tornado = new Tornado(Trigger.TIME, player.getPlayfieldByName(player.getName()));

        tornado.activate();

        assertEquals(player.getPlayfieldByName(player.getName()).getPlayfield(), playerField.getPlayfield());
    }

    private List<Integer> createNewArrayList() {
        return new ArrayList<>();
    }
}
