package game.player.info;

public class Gold {

    private int gold;

    public Gold() {
        gold = 0;
    }

    public int getGold() {
        return gold;
    }


    public void addGold(int gold){
        this.gold += gold;
    }

    public boolean removeGold(int gold){
        if ((this.gold -= gold)>= 0){
            return true; // het is gekocht
        }
        return false; // het is niet gekocht, te weinig geld.
    }

    @Override
    public String toString() {
        return Integer.toString(gold);
    }
}
