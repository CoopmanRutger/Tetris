package game.api.webapi;

import game.player.hero.Hero;

public class HeroController {

    private Hero hero;

    public HeroController(String name) {
        this.hero = new Hero(name);
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(String name) {
        this.hero = new Hero(name);
    }
}
