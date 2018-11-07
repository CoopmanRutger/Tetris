package game.player.hero.ability;

import game.player.playfields.playfield.PointsForAbilities;

public class AbilityLvl3 implements Ability {

    private String name = null;
    private int startValue;
    private Boolean available;
    private static int numberOfTimesUsed = 0;

    public AbilityLvl3(String name) {
        this.name = name;
        this.startValue = 1000;
        this.available = false;
    }

    @Override
    public int getStartValue() {
        return startValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean isAvailable() {
        return available;
    }

    @Override
    public int getNumberOfTimesUsed() {
        return numberOfTimesUsed;
    }

    @Override
    public void abilityIsReadyToUse(PointsForAbilities points) {
        if (points.getPoints() >= startValue){
            available = true;
        }
        // TODO
    }

    @Override
    public void usedAbility() {
        numberOfTimesUsed++;
    }

    @Override
    public void activate(PointsForAbilities points) {
        if (available){
            usedAbility();
            points.removePoints(startValue);
            action();
        } else {
            System.out.println("nope can't");
        }
        // TODO hier activeer je hem
    }

    @Override
    public void action() {
        // TODO hier voer je hem uit
    }

    @Override
    public String toString() {
        return "AbilityLvl1{" +
                "name='" + name + '\'' +
                ", startValue=" + startValue +
                ", available=" + available +
                '}';
    }
}

