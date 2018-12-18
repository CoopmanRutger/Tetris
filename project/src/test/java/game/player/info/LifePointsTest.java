package game.player.info;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LifePointsTest {

    private LifePoints lifepoints;

    @Before
    public void before(){
        lifepoints = new LifePoints();
    }

    @Test
    public void getLifePoints() {
        assertEquals(10, lifepoints.getLifePoints());
    }

    @Test
    public void addLifePoint() {
        assertEquals(10,lifepoints.getLifePoints());
        for (int i = 0; i <3 ; i++) {
            lifepoints.addLifePoint();
        }
        assertEquals(13,lifepoints.getLifePoints());

        // testen boven 15 gaat niet
        for (int i = 0; i <3 ; i++) {
            lifepoints.addLifePoint();
        }

        assertEquals(15, lifepoints.getLifePoints());
    }

    @Test
    public void removeLifePoint() {
        addLifePoint();
        assertEquals(15, lifepoints.getLifePoints());
        lifepoints.removeLifePoint();
        assertEquals(14, lifepoints.getLifePoints());

        //testen onder 0
        for (int i = 0; i <16 ; i++) {
            lifepoints.removeLifePoint();
        }
        assertEquals(0, lifepoints.getLifePoints());

    }

}