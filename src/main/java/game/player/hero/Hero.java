package game.player.hero;

import game.player.hero.ability.Ability;
import org.pmw.tinylog.Logger;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Hero {

    private String name;
    private Set<Ability> abilitySet;

    public Hero(final String name) {
        this.name = name;
        abilitySet = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ability> getAbilitySet() {
        return abilitySet;
    }

    public void addAbility(final Ability ability) {
        if (abilitySet.size() < 2 && !abilitySet.contains(ability)) {
            abilitySet.add(ability);
        } else {
            Logger.warn("Je hebt er al 2 verschillende abilities OF de ability heb je al");
        }
    }

    public void removeAbility(Ability ability) {
        if (abilitySet.contains(ability)) {
            abilitySet.remove(ability);
        } else {
            Logger.warn("deze ability zit niet in je verzameling dus kan je er ook niet uitsmijten.");
        }
    }

    public void setAbilitySet(Set<Ability> abilitySet) {
        this.abilitySet = abilitySet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hero hero = (Hero) o;
        return Objects.equals(name, hero.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }


    @Override
    public String toString() {
        return "Hero{"
            + "name='" + name + '\''
            + ", abilitySet=" + abilitySet
            + '}';
    }
}
