package game.player.hero.ability;

import game.player.playfields.playfield.PointsForAbilities;

/**
 * @author Remote Access Tetris aka RAT
 */

public interface Ability {

    int getStartValue();

    String getName();

    Boolean isAvailable();

    int getNumberOfTimesUsed();

    void abilityIsReadyToUse(PointsForAbilities points);

    void usedAbility();

    void activate(PointsForAbilities points);

    void action();

    @Override
    String toString();
}
