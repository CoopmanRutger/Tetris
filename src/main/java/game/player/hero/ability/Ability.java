package game.player.hero.ability;

public interface Ability {

    String getName();

    int getStartValue();

    int getNumberOfTimesUsed();

    void usedAbility();

    boolean activate();

    void action();

    void stopAction();

    @Override
    String toString();
}
