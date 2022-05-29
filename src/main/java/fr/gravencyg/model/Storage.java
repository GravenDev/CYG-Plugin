package fr.gravencyg.model;

import fr.gravencyg.utils.CLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private String displayName;
    private String uuid;
    private String rank;
    private int level;
    private int trophys;
    private List<Plot> plots;

    public Storage() {
        this.displayName = "Undefined";
        this.uuid = "X9X9";
        this.rank = "Joueur";
        this.plots = new ArrayList<>();
        this.level = 1;
        this.trophys = 0;
    }

    public Storage(Player player) {
        this.displayName = player.getDisplayName();
        this.uuid = player.getUniqueId().toString();
        this.rank = "Joueur";
        this.plots = new ArrayList<>();
        this.level = 1;
        this.trophys = 0;
    }

    public void createPlot(Player player) {
        this.plots.add(new Plot(player));
    }

    public void setPseudo(Player player) {
        this.displayName = player.getDisplayName();
    }

    public void addTrophy(int amount) { this.trophys += amount; }

    public void setLevel(int newLevel) { this.level = newLevel; }

    public String getDisplayName() {
        return displayName;
    }

    public void setRank(String rankS) {
        this.rank = rankS;
    }

    public void setTrophy(int trophys) { this.trophys = trophys;}

    public String getRank() {
        return rank;
    }

    public int getLevel() { return level; }

    public String getUUID() { return uuid; }

    public int getTrophys() { return trophys; }

    public GameConfig getConfig(String worldName) { return getPlot(worldName).getConfig(); }

    public List<Plot> getPlots(){
        return plots;
    }

    public Plot getPlot(String worldName) {
        Bukkit.getServer().broadcastMessage(getPlots().size()+"sss");
        return getPlots().get(Integer.parseInt(worldName.split("_")[1]));
    }
}
