package game.api.webapi;

import game.player.Player;

public class PlayerController {

    private Player player;
    private String username;

    public PlayerController(String playername) {
        player = new Player(playername);
        this.username = playername;
    }

    public Player getPlayer() {
        return player;
    }

    public String getUsername() {
        return username;
    }

    public void setPlayer(String username) {
        this.player = new Player(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
