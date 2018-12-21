package game.player.info;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LifepointsTest {

    private Lifepoints lifepoints;

    @Before
    public void before() {
        lifepoints = new Lifepoints();
    }

    @Test
    public void receiveLifePoints() {
        assertEquals(10, lifepoints.getLifePoints());
    }

    @Test
    public void addLifePoint1() {
        for (int i = 0; i < 3; i++) {
            lifepoints.addLifePoint();
        }
        assertEquals(13, lifepoints.getLifePoints());
    }

    @Test
    public void addLifePoint2() {
        // testen boven 15 gaat niet
        for (int i = 0; i < 5; i++) {
            lifepoints.addLifePoint();
        }
        
    }

    @Test
    public void removeLifePoint1() {
        addLifePoint2();
        lifepoints.removeLifePoint();
        assertEquals(14, lifepoints.getLifePoints());

    }

    @Test
    public void removeLifePoint2() {
        addLifePoint2();
        lifepoints.removeLifePoint();

        //testen onder 0
        for (int i = 0; i < 16; i++) {
            lifepoints.removeLifePoint();
        }
        assertEquals(0, lifepoints.getLifePoints());

    }

}
