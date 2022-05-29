package fr.gravencyg;

import fr.gravencyg.actions.ActionRegisters;
import fr.gravencyg.actions.team.Team;
import fr.gravencyg.commands.admin.AdminForceUpdateHoloCommand;
import fr.gravencyg.commands.game.GameLeaveCommand;
import fr.gravencyg.commands.game.GamePlayCommand;
import fr.gravencyg.commands.admin.AdminForceLevelCommand;
import fr.gravencyg.commands.admin.AdminRankCommand;
import fr.gravencyg.commands.admin.AdminRefreshLevelCommand;
import fr.gravencyg.commands.link.LinkDiscordCommand;
import fr.gravencyg.commands.link.LinkShopCommand;
import fr.gravencyg.commands.link.LinkTwitterCommand;
import fr.gravencyg.commands.manage.ManageFriendCommand;
import fr.gravencyg.commands.manage.ManageLocationCommand;
import fr.gravencyg.commands.manage.ManageMessageCommand;
import fr.gravencyg.commands.manage.ManageVariablesCommand;
import fr.gravencyg.commands.mod.*;
import fr.gravencyg.commands.player.*;
import fr.gravencyg.commands.player.PlayerHatCommand;
import fr.gravencyg.commands.shortcut.ShopPremiumCommand;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.items.ArmorManager;
import fr.gravencyg.listeners.BlockListener;
import fr.gravencyg.listeners.ChatListener;
import fr.gravencyg.listeners.PlayerListener;
import fr.gravencyg.listeners.WorldListener;
import fr.gravencyg.manager.*;
import fr.gravencyg.game.CYGameManager;
import fr.gravencyg.menus.core.CustomMenuListener;
import fr.gravencyg.npcs.NPCClickListener;
import fr.gravencyg.npcs.NPCManager;
import fr.gravencyg.menus.CustomMenuManager;
import fr.gravencyg.schedule.CYGExecutionTask;
import fr.gravencyg.schedule.CYGGameCycle;
import fr.gravencyg.scoreboards.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CYG extends JavaPlugin {

    public Location spawnLocation;
    private Map<Player, Block> eventBlockCache = new HashMap<>();

    private List<Player> freezeGamePlayers = new ArrayList<>();
    private List<Player> freezeModPlayers = new ArrayList<>();

    private Map<Player, String> targetPlayMenu = new HashMap<>();

    private List<BossBar> barActions = new ArrayList<>();
    private CustomMenuManager menuManager;
    private CYGDataManager dataManager;
    private CYGHologramManager hologramManager;
    private CYGCompilerManager compilerManager;
    private ActionRegisters actionRegisters;
    private NPCManager npcManager;
    private ScoreboardManager scoreboardManager;
    private CYGameManager gameManager;
    private CYGWorleditManager worldEditManager;
    private CYGPlotManager plotManager;
    private ArmorManager armorManager;

    @Override
    public void onEnable() {
        System.out.println("CYG >> Enabled");

        World world = Bukkit.getWorld("world");
        world.setDifficulty(Difficulty.PEACEFUL);

        // register locations
        spawnLocation = new Location(world, 0, 28.98, 1.572, 90.1f, -0.2f);

        // register managers

        dataManager = new CYGDataManager(this);
        menuManager = new CustomMenuManager(this);
        hologramManager = new CYGHologramManager(this);
        compilerManager = new CYGCompilerManager(this);
        armorManager = new ArmorManager();
        actionRegisters = new ActionRegisters(this);
        npcManager = new NPCManager(this);
        scoreboardManager = new ScoreboardManager(this);
        gameManager = new CYGameManager(this);
        worldEditManager = new CYGWorleditManager(this);
        plotManager = new CYGPlotManager(this);

        // register commands
        getCommand("hub").setExecutor(new PlayerHubCommand(this));
        getCommand("plot").setExecutor(new PlayerPlotCommand(this));
        getCommand("locations").setExecutor(new ManageLocationCommand(this));
        getCommand("messages").setExecutor(new ManageMessageCommand(this));
        getCommand("play").setExecutor(new GamePlayCommand(this));
        getCommand("rank").setExecutor(new AdminRankCommand(this));
        getCommand("verifygame").setExecutor(new ModVerifyGameCommand(this));
        getCommand("friend").setExecutor(new ManageFriendCommand(this));
        getCommand("fly").setExecutor(new PlayerFlyCommand(this));
        getCommand("mute").setExecutor(new ModMuteCommand(this));
        getCommand("unmute").setExecutor(new ModUnMuteCommand(this));
        getCommand("kick").setExecutor(new ModKickCommand(this));
        getCommand("freeze").setExecutor(new ModFreezeCommand(this));
        getCommand("uuid").setExecutor(new PlayerUUIDCommand(this));
        getCommand("leave").setExecutor(new GameLeaveCommand(this));
        getCommand("barriere").setExecutor(new PlayerBarrierCommand(this));
        getCommand("we").setExecutor(new PlayerWorldEditCommand(this));
        getCommand("ping").setExecutor(new PlayerPingCommand(this));
        getCommand("head").setExecutor(new PlayerHeadCommand(this));
        getCommand("gamemode").setExecutor(new PlayerGameModeCommand(this));
        getCommand("premium").setExecutor(new ShopPremiumCommand(this));
        getCommand("refreshlevel").setExecutor(new AdminRefreshLevelCommand(this));
        getCommand("forcelevel").setExecutor(new AdminForceLevelCommand(this));

        getCommand("twitter").setExecutor(new LinkTwitterCommand(this));
        getCommand("discord").setExecutor(new LinkDiscordCommand(this));
        getCommand("shop").setExecutor(new LinkShopCommand(this));
        getCommand("hat").setExecutor(new PlayerHatCommand(this));

        getCommand("actions").setExecutor(new PlayerActionCommand(this));
        getCommand("ev").setExecutor(new PlayerBlockEventCommand(this));
        getCommand("if").setExecutor(new PlayerBlockConditionCommand(this));
        getCommand("updateholo").setExecutor(new AdminForceUpdateHoloCommand(this));

        getCommand("options").setExecutor(new ShortcutOptionCommand(this));
        getCommand("gamename").setExecutor(new ShortcutGameNameCommand(this));
        getCommand("setcode").setExecutor(new ShortcutSetCodeCommand(this));
        getCommand("setspawn").setExecutor(new ShortcutSetSpawnCommand(this));

        // register listeners
        getServer().getPluginManager().registerEvents(new WorldListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getPluginManager().registerEvents(new NPCClickListener(this), this);
        getServer().getPluginManager().registerEvents(new CustomMenuListener(this), this);

        // starting execution task loop
        CYGExecutionTask task = new CYGExecutionTask(this);
        task.runTaskTimer(this, 2L, 2L);

        // starting game cycle task loop
        CYGGameCycle gameCycle = new CYGGameCycle(this);
        gameCycle.runTaskTimer(this, 20L, 20L);

        for(Player player : Bukkit.getOnlinePlayers())
        {
            if(!player.getWorld().getName().equalsIgnoreCase("world")) {
                hologramManager.registerHologram(player.getWorld());
            }
            scoreboardManager.load(player);
        }

    }

    @Override
    public void onDisable() {
        hologramManager.removeAllHolograms();

        for(Map.Entry<String, List<CYGame>> game : gameManager.getGames().entrySet())
        {
            for(CYGame gam : game.getValue())
            {
                gameManager.regenMap(gam);
            }
        }

        for(BossBar bar : barActions)
        {
            bar.removeAll();
        }

        for(Player player : Bukkit.getOnlinePlayers())
        {
            scoreboardManager.unload(player);
        }

        npcManager.killAll();
        System.out.println("CYG >> Disabled");
    }

    public void toggleGameFreeze(Player player) {
        if(!freezeGamePlayers.contains(player))
        {
            freezeGamePlayers.add(player);
        }
        else
        {
            freezeGamePlayers.remove(player);
        }
    }

    public CustomMenuManager getMenuManager() { return menuManager; }

    public CYGDataManager getDataManager() { return dataManager; }

    public Map<Player, Block> getEventBlockCache() { return eventBlockCache; }

    public CYGHologramManager getHologramManager() { return hologramManager; }

    public CYGCompilerManager getCompilerManager() { return compilerManager; }

    public ActionRegisters getActionBlocks() { return actionRegisters; }

    public ArmorManager getArmorManager() { return armorManager; }

    public NPCManager getNpcManager() { return npcManager; }

    public CYGameManager getGameManager() { return gameManager; }

    public ScoreboardManager getScoreboardManager() { return scoreboardManager; }

    public List<Player> getFreezeModPlayers() { return freezeModPlayers; }

    public List<Player> getFreezeGamePlayers() { return freezeGamePlayers; }

    public CYGWorleditManager getWorldEditManager() {return worldEditManager;}

    public List<BossBar> getBarActions(){ return barActions; }

    public CYGPlotManager getPlotManager(){ return plotManager; }

    public Map<Player, String> getTargetPlayMenu() {
        return targetPlayMenu;
    }
}
