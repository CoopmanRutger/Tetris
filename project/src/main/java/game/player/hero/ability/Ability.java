package game.player.hero.ability;

import game.player.playfield.PointsForAbilities;
import io.netty.util.TimerTask;

public interface Ability {

    int getStartValue();
    int getNumberOfTimesUsed();
    void usedAbility();
    boolean activate();
    void action();
    void stopAction();
    String toString();
}