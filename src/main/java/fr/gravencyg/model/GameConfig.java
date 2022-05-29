package fr.gravencyg.model;

import org.bukkit.entity.Player;

public class GameConfig {

    private String gameName;

    private int autoStartPlayer;

    private int autoStartTime;

    private int maxPlayers;

    private boolean whitelist;

    private int lifes;

    public GameConfig(Player player) {
        this.gameName = "Jeu de " + player.getName();
        this.autoStartPlayer = 2;
        this.maxPlayers = 10;
        this.autoStartTime = 10;
        this.whitelist = true;
        this.lifes = 1;
    }

    public String getGameName() {
        return gameName;
    }

    public int getAutoStartPlayer() {
        return autoStartPlayer;
    }

    public int getAutoStartTime() { return autoStartTime; }

    public boolean isWhitelist() {
        return whitelist;
    }

    public void setAutoStartTime(int autoStartTime) { this.autoStartTime = autoStartTime;}

    public void setLifes(int lifes) { this.lifes = lifes;}

    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers;}

    public void setAutoStartPlayer(int autoStartPlayer) { this.autoStartPlayer = autoStartPlayer;}

    public void setWhitelist(boolean whitelist) { this.whitelist = whitelist;}

    public int getMaxPlayers() {
        if(maxPlayers == 0) return 10;
        return maxPlayers;
    }

    public int getLifes() {
        if(lifes == 0) return 1;
        return lifes;
    }

    public void setName(String name) { this.gameName = name; }
}
