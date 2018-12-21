package tetris.player.hero.ability;

import tetris.player.playfield.Playfield;

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
