package tetris.webapi;

import tetris.events.Events;
import tetris.events.event.Trigger;
import tetris.player.info.Gold;
import tetris.player.info.Info;
import tetris.player.info.Lifepoints;

public class IntelController {

    private Info info = new Info();
    private Gold gold = new Gold();
    private Lifepoints lifepoints = new Lifepoints();

    private Trigger trigger1 = Trigger.SCORE;
    private Trigger trigger2 = Trigger.TIME;

    private Events events = new Events();


    public Events getEvents() {
        return events;
    }

    public Trigger getTrigger1() {
        return trigger1;
    }

    public Trigger getTrigger2() {
        return trigger2;
    }

    public Lifepoints getLifepoints() {
        return lifepoints;
    }

    public Info getInfo() {
        return info;
    }

    public Gold getGold() {
        return gold;
    }
}
