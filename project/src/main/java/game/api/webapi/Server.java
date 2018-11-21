package game.api.webapi;

import game.Game;
import game.api.Tetris;
import game.api.jdbcinteractor.ConnectionDatabase;
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
import io.vertx.core.Vertx;

public class Server extends AbstractVerticle {


    @Override
    public void start(){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ConnectionDatabase());
        vertx.deployVerticle(new Tetris());
        vertx.deployVerticle(new WebAPI());

    }

    public void gamePlay(){

        Trigger trigger1 = Trigger.SCORE;
        Trigger trigger2 = Trigger.TIME;

        Event event1 = new Event("kill them", trigger1 );
        Event event2 = new Event("add some blocks", trigger2);

        Events events = new Events();
        events.addEvent(event1);
        events.addEvent(event2);

        Hero hero = new Hero("Mathias de boer");
        Hero hero1 = new Hero("Jillke de ridder");

        Player player = new Player("rutger");
        Player player1 = new Player("jan");

        player.setHero(hero);
        player1.setHero(hero1);


        Ability ability1 = new AbilityLvl1("one");
        Ability ability2 = new AbilityLvl2("paper");
        Ability ability3 = new AbilityLvl3("car");

        hero.addAbility(ability1);
        hero.addAbility(ability2);
        hero1.addAbility(ability1);
        hero1.addAbility(ability3);

        Info info = new Info();
        Gold gold = new Gold();
        gold.addGold(500);

        Lifepoints lifepoints = new Lifepoints();

        Info info1 = new Info();

        player.setInfo(info);
        player1.setInfo(info1);

        Playfields playfields = new Playfields();

        Playfield playfield = new Playfield();

        PointsForAbilities points = new PointsForAbilities();
        points.addPoints(200);
        playfield.getPoints().addPoints(points.getPoints());

        playfields.addPlayfield(playfield);

        player.setPlayfields(playfields);


        Game game = new Game(events);
        game.addPlayer(player);
        game.addPlayer(player1);


        Routes routes = new Routes(game);
//        routes.homeScreen();

        routes.battleFieldStart();

//        routes.battleFieldBlockPositioning();

//        routes.chooseFaction();

//        routes.getFactionInfo();

//        routes.sendBlockOneByOne(game);


    }
}
