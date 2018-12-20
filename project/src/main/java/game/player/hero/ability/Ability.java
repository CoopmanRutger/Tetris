package game.player.hero.ability;

import game.player.playfield.PointsForAbilities;

public interface Ability {

    int getStartValue();
    int getNumberOfTimesUsed();
    void usedAbility();
    void activate(PointsForAbilities points);
    void action();
    String toString();
}