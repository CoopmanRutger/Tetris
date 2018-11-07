package game.player.hero.ability;

import game.player.playfields.playfield.PointsForAbilities;

public interface Ability {

    int getStartValue();
    String getName();
    Boolean isAvailable();
    int getNumberOfTimesUsed();
    void abilityIsReadyToUse(PointsForAbilities points);
    void usedAbility();
    void activate(PointsForAbilities points);
    void action();
    String toString();
}