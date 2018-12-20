package game.player.hero.ability;

import game.player.playfield.PointsForAbilities;

public interface Ability {

    int getStartValue();
    Boolean isAvailable();
    int getNumberOfTimesUsed();
    void abilityIsReadyToUse(PointsForAbilities points);
    void usedAbility();
    void activate(PointsForAbilities points);
    void action();
    String toString();
}