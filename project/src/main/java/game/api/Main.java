package game.api;

import game.Game;
import game.events.Events;
import game.events.event.Event;
import game.events.event.Trigger;
import game.player.Player;
import game.player.hero.Hero;
import game.player.hero.ability.*;
import game.player.info.Gold;
import game.player.info.Info;
import game.player.info.Lifepoints;
import game.player.playfields.Playfields;
import game.player.playfields.playfield.Playfield;
import game.player.playfields.playfield.PointsForAbilities;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }
    private void run() {

        Trigger trigger1 = Trigger.SCORE;
        Trigger trigger2 = Trigger.TIME;

        Event event1 = new Event("kill them", trigger1 );
        Event event2 = new Event("add some blocks", trigger2);

        Events events = new Events();
        events.addEvent(event1);
        events.addEvent(event2);

        Game game = new Game(events);

        Player player = new Player("1ste man");
        Player player1 = new Player("2de man");
        Player player2 = new Player("3de man");
        Player player3 = new Player("4de man");
        Player player4 = new Player("5de man");
        Player player5 = new Player("6de man");



        game.addPlayer(player);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);
        game.addPlayer(player5);

        Hero hero = new Hero("Cristiaan de ridder");
        Hero hero1 = new Hero("Fredrick de kok");
        Hero hero2 = new Hero("Mathias de boer");
        Hero hero3 = new Hero("Jillke de ridder");
        Hero hero4 = new Hero("Kristien de boerin");
        Hero hero5 = new Hero("Heidi de kokin");


        player.setHero(hero);
        player1.setHero(hero1);
        player2.setHero(hero2);
        player3.setHero(hero3);
        player4.setHero(hero4);
        player5.setHero(hero5);



        Ability ability1 = new AbilityLvl1("one");
        Ability ability2 = new AbilityLvl2("paper");
        Ability ability3 = new AbilityLvl3("car");

        PointsForAbilities points = new PointsForAbilities();
        points.addPoints(200);
        points.addPoints(200);
        points.addPoints(200);
        points.addPoints(200);
        points.addPoints(200);
//        points.removePoints(500);

        ability3.abilityIsReadyToUse(points);
//        ability3.activate(points);

//        System.out.println(ability1);


//        hero.addAbility(ability1);
//        System.out.println(hero);
        hero.addAbility(ability1);
        hero.addAbility(ability2);
//        System.out.println(hero);


        hero.removeAbility(ability2);
//        hero.removeAbility(ability3);
        hero.addAbility(ability3);
//
        System.out.println(hero);



        Info info = new Info();

        Gold gold = new Gold();
        gold.addGold(500);
//        info.setGold(gold);

        Lifepoints lifepoints = new Lifepoints();
//        lifepoints.getLifePoints();
//        System.out.println(lifepoints);
//        lifepoints.addLifePoint();
//        System.out.println(lifepoints);
//        lifepoints.removeLifePoint();
//        System.out.println(lifepoints);
//        info.setLifepoints(lifepoints);
//        System.out.println(player1.getInfo());


        player1.setInfo(info);
//        System.out.println(player1.getInfo());



        Playfields playfields = new Playfields();

        Playfield playfield = new Playfield();
        System.out.println(points);
        playfield.getPoints().addPoints(points.getPoints());

        playfields.addPlayfield(playfield);

        player.setPlayfields(playfields);




        System.out.println(player1);
        System.out.println(player);




    }
}
