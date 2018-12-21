package game.player.hero.ability;

import org.junit.Before;
import org.junit.Test;
import tetris.player.Player;
import tetris.player.hero.ability.Ability;
import tetris.player.hero.ability.CheeringCrowd;
import tetris.player.hero.ability.Joker;
import tetris.player.playfield.Playfield;
import tetris.player.playfield.PointsForAbilities;

import static org.junit.Assert.assertEquals;

public class AbilityTest {

    private static final String DINS = "Dins";
    private Ability ability1;
    private Ability ability2;

    private PointsForAbilities points;

    @Before
    public void before() {
        Player player = new Player(DINS);
        Playfield playfield = new Playfield(20, 12);
        player.addPlayfield(DINS, playfield);
        ability1 = new CheeringCrowd(player.getPlayfieldByName(player.getName()));
        ability2 = new Joker(player.getPlayfieldByName(player.getName()));
        points = new PointsForAbilities();
    }

    @Test
    public void receiveNumberOfTimesUsed1() {
        points.addPoints(10);
        ability1.getPlayfield().setPoints(points);
        //ability1.abilityIsReadyToUse(points);
        ability1.activate();
        assertEquals(1, ability1.getNumberOfTimesUsed());
    }

    @Test
    public void receiveNumberOfTimesUsed2() {

        for (int i = 0; i < 6; i++) {
            points.addPoints(18);
            ability2.getPlayfield().setPoints(points);
            //ability2.abilityIsReadyToUse(points);
            ability2.activate();
        }
        assertEquals(6, ability2.getNumberOfTimesUsed());
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
