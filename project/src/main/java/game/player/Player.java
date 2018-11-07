package game.player;

import game.player.hero.Hero;
import game.player.info.Info;
import game.player.playfields.Playfields;

import java.util.Objects;

public class Player {

    private String name;
    private Hero hero;
    private Playfields playfields;
    private Info info;

    public Player(String name) {
        this.name = name;
        playfields = new Playfields();
        info = new Info();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Playfields getPlayfields() {
        return playfields;
    }

    public void setPlayfields(Playfields playfields) {
        this.playfields = playfields;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hero=" + hero +
                ", playfields=" + playfields +
                ", info=" + info +
                '}';
    }
}
