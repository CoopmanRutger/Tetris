package game.player.hero.ability;

import game.player.playfield.PointsForAbilities;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbilityTest {

    private Ability ability1;
    private Ability ability2;
    private Ability ability3;
    private PointsForAbilities points ;

    @Before
    public void before(){
        ability1 = new CheeringCrowd("one");
        ability2 = new Joker("paper");
        ability3 = new AbilityLvl3("car");
        points = new PointsForAbilities();
    }

    @Test
    public void getStartValue() {
        assertEquals(500, ability1.getStartValue());
        assertEquals(750, ability2.getStartValue());
        assertEquals(1000, ability3.getStartValue());
    }

    @Test
    public void getName() {
        assertEquals("one", ability1.getName());
        assertEquals("paper", ability2.getName());
        assertEquals("car", ability3.getName());
    }


    @Test
    public void isAvailable() {
        assertFalse(ability1.isAvailable());
        points.addPoints(500);
        ability1.abilityIsReadyToUse(points);
        assertTrue(ability1.isAvailable());

        //er zijn maar 500 points -> 750 nodig
        ability2.abilityIsReadyToUse(points);
        assertFalse(ability2.isAvailable());

        // er zijn 800 punten
        points.addPoints(300);
        ability1.abilityIsReadyToUse(points);
        assertTrue(ability1.isAvailable());
        ability2.abilityIsReadyToUse(points);
        assertTrue(ability2.isAvailable());
        ability3.abilityIsReadyToUse(points);
        assertFalse(ability3.isAvailable());

        //er zijn 1100 punten
        points.addPoints(300);
        ability2.abilityIsReadyToUse(points);
        assertTrue(ability2.isAvailable());
        ability3.abilityIsReadyToUse(points);
        assertTrue(ability3.isAvailable());

    }

    @Test
    public void getNumberOfTimesUsed() {
        assertEquals(0, ability1.getNumberOfTimesUsed());
        points.addPoints(500);
        ability1.abilityIsReadyToUse(points);
        ability1.activate(points);
        assertEquals(1, ability1.getNumberOfTimesUsed());

        assertEquals(0,ability2.getNumberOfTimesUsed());
        for (int i = 0; i < 7 ; i++) {
            points.addPoints(500);
            ability2.abilityIsReadyToUse(points);
            ability2.activate(points);
        }
        assertEquals(6,ability2.getNumberOfTimesUsed());
    }

    @Test
    public void abilityIsReadyToUse() {

        assertFalse(ability1.isAvailable());

        points.addPoints(500);
        ability1.abilityIsReadyToUse(points);
        assertTrue(ability1.isAvailable());

    }

    @Test
    public void usedAbility() {
        assertFalse(ability1.isAvailable());
        assertFalse(ability2.isAvailable());
        assertFalse(ability3.isAvailable());

        points.addPoints(500);
        ability1.abilityIsReadyToUse(points);
        ability2.abilityIsReadyToUse(points);
        ability3.abilityIsReadyToUse(points);
        assertTrue(ability1.isAvailable());
        assertFalse(ability2.isAvailable());

        points.addPoints(250);
        ability1.abilityIsReadyToUse(points);
        ability2.abilityIsReadyToUse(points);
        ability3.abilityIsReadyToUse(points);
        assertTrue(ability2.isAvailable());
        assertFalse(ability3.isAvailable());

        points.addPoints(250);
        ability1.abilityIsReadyToUse(points);
        ability2.abilityIsReadyToUse(points);
        ability3.abilityIsReadyToUse(points);
        assertTrue(ability3.isAvailable());
        assertTrue(ability2.isAvailable());
        assertTrue(ability1.isAvailable());

    }

    @Test
    public void activate() {
        points.addPoints(500);
        ability1.abilityIsReadyToUse(points);
        ability1.activate(points);
        assertEquals(0, points.getPoints());

        points.addPoints(1000);
        assertEquals(1000, points.getPoints());
        ability2.abilityIsReadyToUse(points);
        ability2.activate(points);
        assertEquals(250, points.getPoints());
    }

    @Test
    public void action() {
        // TODO: 07/11/2018
    }
}