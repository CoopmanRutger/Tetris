package game.player;

import game.player.hero.Hero;
import game.player.info.Info;
import game.player.playfields.Playfields;

import java.util.Objects;

/**
 * @author Remote Access Tetris aka RAT
 */

public class Player {

    private String name;
    private Hero hero;
    private Playfields playfields;
    private Info info;

    public Player(final String name) {
        this.name = name;
        playfields = new Playfields();
        info = new Info();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    public Playfields getPlayfields() {
        return playfields;
    }

    public void setPlayfields(final Playfields playfields) {
        this.playfields = playfields;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(final Info info) {
        this.info = info;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{"
            + "name='" + name + '\''
            + ", hero=" + hero
            + ", playfields=" + playfields
            + ", info=" + info
            + '}';
    }
}
