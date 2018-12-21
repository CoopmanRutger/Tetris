package game.api.webapi;

import game.Game;
import game.events.Events;
import game.events.event.Trigger;
import game.player.Player;
import game.player.hero.Hero;
import game.player.hero.ability.Ability;
import game.player.hero.ability.CheeringCrowd;
import game.player.hero.ability.Joker;
import game.player.info.Gold;
import game.player.info.Info;
import game.player.info.Lifepoints;
import game.player.playfield.Playfield;
import game.player.playfield.PointsForAbilities;
import io.vertx.core.AbstractVerticle;

/**
 * @author      Remote Access Tetris aka RAT
 */
public class GameController extends AbstractVerticle {

    private Game game = null;
    private String username1;
    private Player player1;
    private Hero hero1;



    private String username2;
    private Player player2;
    private Hero hero2;

    @Override
    public void start(){
        System.out.println("Game Controller started");
    }

    public Game getGame(){
        gamePlay();
        return game;
    }

    public void setUsername1(String username){
        this.username1 = username;
    }

    public void setPlayer1(String playerName){
        player1 = new Player(playerName);
    }

    public void setHero1(String heroName){
        hero1 = new Hero(heroName);
    }



    public void setUsername2(String username){
        this.username2 = username;
    }

   private void gamePlay(){
        setUsername1("Rutger123");
        setUsername2("User2");

        Trigger trigger1 = Trigger.SCORE;
        Trigger trigger2 = Trigger.TIME;

        //Event event1 = new Event("kill them", trigger1 );
        //Event event2 = new Event("add some blocks", trigger2);

        Events events = new Events();
        //events.addEvent(event1);
        //events.addEvent(event2);

        hero1 = new Hero("Mathias de boer");
        hero2 = new Hero("Jillke de ridder");

        player1 = new Player(username1);
        player2 = new Player(username2);



        Info info = new Info();
        Gold gold = new Gold();
        gold.addGold(500);

        Lifepoints lifepoints = new Lifepoints();

        Info info1 = new Info();

        player1.setInfo(info);
        player2.setInfo(info1);


        Playfield playfield1 = new Playfield(20,12);
        Playfield playfield2 = new Playfield(20,12);


       Ability ability1 = new CheeringCrowd(playfield1);
       Ability ability2 = new Joker(playfield1);
       Ability ability3 = new Joker(playfield2);
       Ability ability4 = new CheeringCrowd(playfield2);;

       hero1.addAbility(ability2);
       hero1.addAbility(ability1);
       hero2.addAbility(ability3);
       hero2.addAbility(ability4);

       player1.setHero(hero1);
       player2.setHero(hero2);

        player1.addPlayfield(username1, playfield1);
        player1.addPlayfield(username2, playfield2);

        player2.addPlayfield(username2, playfield2);
        player2.addPlayfield(username1, playfield1);

        game = new Game(events);
        game.addPlayer(player1);
        game.addPlayer(player2);

    }
}
