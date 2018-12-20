package game.player;

import game.player.hero.Hero;
import game.player.info.Info;
import game.player.playfield.Playfield;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player {

    private String name;
    private Hero hero;
    private Map<String, Playfield> playfields;
    private Info info;

    public Player(String name) {
        this.name = name;
        playfields = new HashMap<>();
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

    public Map<String, Playfield> getPlayfields() {
        return playfields;
    }

    public Playfield getPlayfieldByName(String playername) {
        return playfields.get(playername);
    }

    public void setPlayfields(Map<String, Playfield> playfields) {
        this.playfields = playfields;
    }

    public void addPlayfield(String playername, Playfield playfield) {
        playfields.put(playername, playfield);
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
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
