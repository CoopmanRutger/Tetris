package game.player.info;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XpTest {

    private Xp xp;

    @Before
    public void initiate() {
        xp = new Xp();
    }

    @Test
    public void testUpdateXp() {
        xp.updateXp();
        assertEquals(100, xp.getXp());
    }

    @Test
    public void testCheckLevel1() {
        xp.updateXp();
        xp.updateXp();
        xp.updateXp();
        assertEquals(300, xp.getXp());
    }

    @Test
    public void testCheckLevel2() {
        xp.updateXp();
        xp.updateXp();
        xp.updateXp();
        assertEquals(2, xp.getLevel());
    }

}
