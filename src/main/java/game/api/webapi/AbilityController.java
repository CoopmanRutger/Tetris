package game.api.webapi;

import game.player.hero.ability.Ability;
import game.player.hero.ability.CheeringCrowd;
import game.player.hero.ability.Joker;

public class AbilityController {

    private Ability ability;

    private GameController controller = new GameController();

    private Ability ability1 = new CheeringCrowd(controller.getPlayfield1());
    private Ability ability2 = new Joker(controller.getPlayfield1());
    private Ability ability3 = new CheeringCrowd(controller.getPlayfield2());
    private Ability ability4 = new Joker(controller.getPlayfield2());

    public void setCheeringCrowd(CheeringCrowd ability) {
        this.ability = ability;
    }

    public void setJoker(Joker ability) {
        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    public Ability getAbility1() {
        return ability1;
    }

    public Ability getAbility2() {
        return ability2;
    }

    public Ability getAbility3() {
        return ability3;
    }

    public Ability getAbility4() {
        return ability4;
    }
}
