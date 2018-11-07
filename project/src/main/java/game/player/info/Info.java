package game.player.info;

public class Info {

    private Gold gold;
    private Lifepoints lifepoints;

    public Info() {
        gold = new Gold();
        lifepoints = new Lifepoints();
    }

    public int getGold() {
        return Integer.parseInt(gold.toString());
    }

    public int getLifepoints() {
        return Integer.parseInt(lifepoints.toString());
    }

    @Override
    public String toString() {
        return "Info{" +
                "gold=" + gold +
                ", lifepoints=" + lifepoints +
                '}';
    }
}