package game.player.info;

public class Info {

    private Gold gold;
    private Lifepoints lifepoints;
    private Xp xp;

    public Info() {
        gold = new Gold();
        lifepoints = new Lifepoints();
        xp = new Xp();
    }

    public int getGold() {
        return Integer.parseInt(gold.toString());
    }

    public int getLifepoints() {
        return Integer.parseInt(lifepoints.toString());
    }

    public int getXp() {
        return xp.getXp();
    }

    public int getLevel() {
        return xp.getLevel();
    }

    @Override
    public String toString() {
        return "Info{" +
                "gold=" + gold +
                ", lifepoints=" + lifepoints +
                '}';
    }
}