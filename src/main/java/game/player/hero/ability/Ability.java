package game.player.hero.ability;

import game.player.playfield.Playfield;

public interface Ability {

    String getName();

    int getStartValue();

    int getNumberOfTimesUsed();

    void usedAbility();

    boolean activate();

    void action();

    void stopAction();

    Playfield getPlayfield();

    @Override
    String toString();
}
