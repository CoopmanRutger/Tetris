package game.player.info;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoldTest {

    private Gold gold;

    @Before
    public void before(){
        gold = new Gold();
    }

    @Test
    public void getGold() {
        assertEquals(0,gold.getGold());
    }

    @Test
    public void addGold() {
        assertEquals(0,gold.getGold());
       gold.addGold(40);
        assertEquals(40,gold.getGold() );
    }

    @Test
    public void removeGold() {
        addGold();
        assertEquals(40, gold.getGold());
        System.out.println(gold);
        gold.removeGold(20);
        System.out.println(gold);
        assertEquals(20,gold.getGold());
    }
}