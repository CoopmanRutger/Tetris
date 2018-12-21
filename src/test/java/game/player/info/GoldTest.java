package game.player.info;

import org.junit.Before;
import org.junit.Test;
import tetris.player.info.Gold;

import static org.junit.Assert.assertEquals;

public class GoldTest {

    private Gold gold;

    @Before
    public void before() {
        gold = new Gold();
    }

    @Test
    public void receiveGold() {
        assertEquals(0, gold.getGold());
    }

    @Test
    public void addGold() {
        gold.addGold(40);
        assertEquals(40, gold.getGold());
    }

    @Test
    public void removeGold() {
        addGold();
        gold.removeGold(20);
        assertEquals(20, gold.getGold());
    }
}
