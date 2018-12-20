package game.player.hero.ability;

import game.player.Player;
import game.player.playfield.Playfield;
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
        Player player = new Player("Dins");
        Playfield playfield = new Playfield(20, 12);
        player.addPlayfield("Dins", playfield);
        ability1 = new CheeringCrowd(player.getPlayfieldByName(player.getName()));
        ability2 = new Joker(player.getPlayfieldByName(player.getName()));
        ability3 = new AbilityLvl3("Jefke");
        points = new PointsForAbilities();
    }

    @Test
    public void getNumberOfTimesUsed() {
        assertEquals(0, ability1.getNumberOfTimesUsed());
        points.addPoints(500);
        //ability1.abilityIsReadyToUse(points);
        ability1.activate(points);
        assertEquals(1, ability1.getNumberOfTimesUsed());

        assertEquals(0,ability2.getNumberOfTimesUsed());
        for (int i = 0; i < 7 ; i++) {
            points.addPoints(500);
            //ability2.abilityIsReadyToUse(points);
            ability2.activate(points);
        }
        assertEquals(6,ability2.getNumberOfTimesUsed());
    }

    @Test
    public void abilityIsReadyToUse() {

//        assertFalse(ability1.isAvailable());
//
//        points.addPoints(500);
//        ability1.abilityIsReadyToUse(points);
//        assertTrue(ability1.isAvailable());

    }

    @Test
    public void usedAbility() {
//        assertFalse(ability1.isAvailable());
//        assertFalse(ability2.isAvailable());
//        assertFalse(ability3.isAvailable());
//
//        points.addPoints(500);
//        ability1.abilityIsReadyToUse(points);
//        ability2.abilityIsReadyToUse(points);
//        ability3.abilityIsReadyToUse(points);
//        assertTrue(ability1.isAvailable());
//        assertFalse(ability2.isAvailable());
//
//        points.addPoints(250);
//        ability1.abilityIsReadyToUse(points);
//        ability2.abilityIsReadyToUse(points);
//        ability3.abilityIsReadyToUse(points);
//        assertTrue(ability2.isAvailable());
//        assertFalse(ability3.isAvailable());
//
//        points.addPoints(250);
//        ability1.abilityIsReadyToUse(points);
//        ability2.abilityIsReadyToUse(points);
//        ability3.abilityIsReadyToUse(points);
//        assertTrue(ability3.isAvailable());
//        assertTrue(ability2.isAvailable());
//        assertTrue(ability1.isAvailable());

    }

    @Test
    public void activate() {
//        points.addPoints(500);
//        ability1.abilityIsReadyToUse(points);
//        ability1.activate(points);
//        assertEquals(0, points.getPoints());
//
//        points.addPoints(1000);
//        assertEquals(1000, points.getPoints());
//        ability2.abilityIsReadyToUse(points);
//        ability2.activate(points);
//        assertEquals(250, points.getPoints());
    }

    @Test
    public void action() {
        // TODO: 07/11/2018
    }
}