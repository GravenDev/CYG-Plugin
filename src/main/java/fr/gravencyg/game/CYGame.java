package fr.gravencyg.game;

import fr.gravencyg.actions.team.Team;
import fr.gravencyg.model.CYGameMode;
import fr.gravencyg.model.GameConfig;
import fr.gravencyg.model.MaterialAndData;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CYGame {

    private Storage storage;
    private GameConfig config;
    private Map<Team, List<Player>> teams;
    private List<Player> players;
    private int countdown;
    private CYGameMode gameMode;

    private boolean canFallDamage;
    private boolean canBuild;
    private boolean canFight;

    private Map<Location, MaterialAndData> breakBlocks;
    private List<Block> placedBlocks;
    private Map<Player, Integer> lifes;

    private int minPlayers;
    private CYGameState state;
    private int lifesPerPlayer;
    private int maxPlayers;
    private String ownerUUID;
    private int gameId;

    public CYGame(Storage storage, int gameId) {
        this.storage = storage;
        this.config = storage.getPlots().get(gameId).getConfig();
        this.ownerUUID = storage.getUUID();
        this.gameId = gameId;
        this.setup();
    }

    public void setup() {
        this.players = new ArrayList<>();
        this.teams = new HashMap<>();
        this.countdown = config.getAutoStartTime();
        this.minPlayers = config.getAutoStartPlayer();
        this.maxPlayers = config.getMaxPlayers();
        this.lifes = new HashMap<>();
        this.state = CYGameState.WAITING;
        this.gameMode = CYGameMode.DEATH_MATCH;
        this.lifesPerPlayer = config.getLifes();
        this.canBuild = false;
        this.canFight = false;

        this.clearRegenSave();
    }

    public void createTeam(Team team) {
        teams.put(team, new ArrayList<>());
    }

    public void clearRegenSave() {
        this.breakBlocks = new HashMap<>();
        this.placedBlocks = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        this.lifes.put(player, lifesPerPlayer);
    }

    public void removeLife(Player player){
        if(lifes.containsKey(player))
        {
            int currentLife = lifes.get(player);
            lifes.put(player, currentLife - 1);
        }
    }

    public boolean canRespawn(Player player){
        if(lifes.containsKey(player))
        {
            return lifes.get(player) >= 1;
        }

        return false;
    }

    public boolean sameTeam(Player player1, Player player2)
    {
        if(getTeamPlayer(player1).equals(getTeamPlayer(player2)))
        {
            return true;
        }
        return false;
    }

    public void eliminatePlayer(Player player) {
        this.players.remove(player);
        this.lifes.remove(player);

        if(hasTeamSystem())
        {
            Team t = getTeamPlayer(player);
            getTeams().get(t).remove(player); // remove player from team
        }

    }

    public boolean hasTeamSystem(){
        return getTeams().size() >= 2;
    }

    public Team getTeamPlayer(Player player)
    {
        Team tm = Team.BLUE;

        for(Map.Entry<Team, List<Player>> t : getTeams().entrySet())
        {
            if(t.getValue().contains(player))
            {
                tm = t.getKey();
                break;
            }
        }

        return tm;
    }

    public void setCountdown(int i) { this.countdown = i;}

    public void setState(CYGameState state) { this.state = state; }

    public void togglePvP() { this.canFight = !canFight; }

    public void toggleBuild() { this.canBuild = !canBuild; }

    public void toggleFallDamage() { this.canFallDamage = !canFallDamage; }

    public void decrementCountdown(){ this.countdown -= 1; }

    public Storage getStorage() { return storage; }

    public List<Player> getPlayers() { return players; }

    public String getName() { return config.getGameName(); }

    public Map<Location, MaterialAndData> getBreakBlocks(){ return breakBlocks; }

    public List<Block> getPlacedBlocks(){ return placedBlocks; }

    public boolean canBuild() { return canBuild; }

    public boolean canFight() { return canFight; }

    public boolean hasStarted() { return state == CYGameState.PLAYING; }

    public int getAutoStartMin() { return config.getAutoStartPlayer(); }

    public boolean isState(CYGameState state) { return this.state == state; }

    public boolean isMode(CYGameMode mode) { return this.gameMode == mode; }

    public boolean isTeamExist(Team team){ return teams.containsKey(team); }

    public CYGameState getState() { return state; }

    public int getCountdown() { return countdown; }

    public boolean canDamageFall() { return canFallDamage; }

    public int getMaxPlayers() { return maxPlayers; }

    public int getPlayerIndex(Player player) {
        int i = 0;
        for(Player pl : players)
        {
            if(pl.equals(player)) return i;
            i++;
        }
        return 0;
    }


    public Map<Team, List<Player>> getTeams() { return teams; }

    public int getLife(Player player) {
        return lifes.get(player);
    }

    public String getUUID() {
        return ownerUUID;
    }

    public int getGameId() {
        return gameId;
    }
}
