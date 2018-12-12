package game.api.webapi;

import game.Game;
import game.events.Events;
import game.events.event.Event;
import game.events.event.Trigger;
import game.player.Player;
import game.player.hero.Hero;
import game.player.hero.ability.Ability;
import game.player.hero.ability.AbilityLvl1;
import game.player.hero.ability.AbilityLvl2;
import game.player.hero.ability.AbilityLvl3;
import game.player.info.Gold;
import game.player.info.Info;
import game.player.info.Lifepoints;
import game.player.playfields.Playfields;
import game.player.playfields.playfield.Playfield;
import game.player.playfields.playfield.PointsForAbilities;
import io.vertx.core.AbstractVerticle;

public class GameController extends AbstractVerticle {

    Game game = null;
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
        System.out.println(game);
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

   public void gamePlay(){

//        String usernamePlayer1 = username1;
//        String usernamePlayer2 = username2;

        Trigger trigger1 = Trigger.SCORE;
        Trigger trigger2 = Trigger.TIME;

        Event event1 = new Event("kill them", trigger1 );
        Event event2 = new Event("add some blocks", trigger2);

        Events events = new Events();
        events.addEvent(event1);
        events.addEvent(event2);

        hero1 = new Hero("Mathias de boer");
        hero2 = new Hero("Jillke de ridder");

//       player1 = new Player(username1);
//       player2 = new Player(username2);
       player1 = new Player("Rutger123");
       player2 = new Player("louis");

        player1.setHero(hero1);
        player2.setHero(hero2);

        Ability ability1 = new AbilityLvl1("destruction");
        Ability ability2 = new AbilityLvl2("lol");
        Ability ability3 = new AbilityLvl3("spook");

        hero1.addAbility(ability1);
        hero1.addAbility(ability2);
        hero2.addAbility(ability1);
        hero2.addAbility(ability3);

        Info info = new Info();
        Gold gold = new Gold();
        gold.addGold(500);

        Lifepoints lifepoints = new Lifepoints();

        Info info1 = new Info();

        player1.setInfo(info);
        player2.setInfo(info1);

        Playfields playfields = new Playfields();

        Playfield playfield = new Playfield();

        PointsForAbilities points = new PointsForAbilities();
        points.addPoints(200);
        playfield.getPoints().addPoints(points.getPoints());

        playfields.addPlayfield(playfield);

        player1.setPlayfields(playfields);


        game = new Game(events);
        game.addPlayer(player1);
        game.addPlayer(player2);

    }
}
