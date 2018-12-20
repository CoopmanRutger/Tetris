package game.player.info;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InfoTest {

    private Info info;

    @Before
    public void before(){
        info = new Info();

    }

    @Test
    public void getGold() {
        assertEquals(0,info.getGold());
    }

    @Test
    public void getLifepoints() {
        assertEquals(10,info.getLifepoints());
    }

    @Test
    public void testXp() {
        assertEquals(0, info.getXp());
    }

}