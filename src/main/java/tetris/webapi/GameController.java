package tetris.webapi;

import io.vertx.core.AbstractVerticle;
import org.pmw.tinylog.Logger;
import tetris.Game;
import tetris.player.playfield.Playfield;

/**
 * @author Remote Access Tetris aka RAT
 */
public class GameController extends AbstractVerticle {

    private Game game;
    private PlayerController player1 = new PlayerController("Rutger123");
    private PlayerController player2 = new PlayerController("User2");
    private HeroController hero1 = new HeroController("Mathias de boer");

    private HeroController hero2 = new HeroController("Jillke de ridder");

    private IntelController intel = new IntelController();
    private Playfield playfield1 = new Playfield(20, 12);
    private Playfield playfield2 = new Playfield(20, 12);


    public PlayerController getPlayer1() {
        return player1;
    }

    public PlayerController getPlayer2() {
        return player2;
    }

    public Playfield getPlayfield1() {
        return playfield1;
    }

    public Playfield getPlayfield2() {
        return playfield2;
    }

    @Override
    public void start() {
        Logger.info("Game Controller started");
    }

    public Game getGame() {
        gamePlay();
        return game;
    }

    private void gamePlay() {


        //Event event1 = new Event("kill them", trigger1 );
        //Event event2 = new Event("add some blocks", trigger2);

        //events.addEvent(event1);
        //events.addEvent(event2);


        intel.getGold().addGold(500);

        IntelController info1 = new IntelController();

        player1.getPlayer().setInfo(intel.getInfo());
        player2.getPlayer().setInfo(info1.getInfo());

        AbilityController abilityController = new AbilityController();

        hero1.getHero().addAbility(abilityController.getAbility1());
        hero1.getHero().addAbility(abilityController.getAbility1());
        hero2.getHero().addAbility(abilityController.getAbility3());
        hero2.getHero().addAbility(abilityController.getAbility4());

        player1.getPlayer().setHero(hero1.getHero());
        player2.getPlayer().setHero(hero2.getHero());

        player1.getPlayer().addPlayfield(player1.getUsername(), playfield1);
        player1.getPlayer().addPlayfield(player2.getUsername(), playfield2);

        player2.getPlayer().addPlayfield(player2.getUsername(), playfield2);
        player2.getPlayer().addPlayfield(player1.getUsername(), playfield1);

        game = new Game(intel.getEvents());
        game.addPlayer(player1.getPlayer());
        game.addPlayer(player2.getPlayer());


        player1.getPlayer().setHero(hero1.getHero());
        player2.getPlayer().setHero(hero2.getHero());

        player1.getPlayer().addPlayfield(player1.getUsername(), playfield1);
        player1.getPlayer().addPlayfield(player2.getUsername(), playfield2);

        player2.getPlayer().addPlayfield(player2.getUsername(), playfield2);
        player2.getPlayer().addPlayfield(player1.getUsername(), playfield1);

        game = new Game(intel.getEvents());
        game.addPlayer(player1.getPlayer());
        game.addPlayer(player2.getPlayer());

    }
}
