package fr.gravencyg.commands;

import fr.gravencyg.CYG;
import fr.gravencyg.game.CYGameManager;
import fr.gravencyg.manager.CYGDataManager;
import fr.gravencyg.manager.CYGWorleditManager;
import fr.gravencyg.menus.CustomMenuManager;
import fr.gravencyg.model.Plot;
import fr.gravencyg.scoreboards.ScoreboardManager;
import fr.gravencyg.utils.CLevel;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

 public abstract class TCommand implements CommandExecutor {

    protected CYG main;
    private boolean allowedInGame;
    private boolean plotOnly;
    private boolean modOnly;
    private boolean allowedInHub;
    private CLevel minLevelRequire;

    public TCommand(CYG main, CLevel minLevelRequire, boolean modOnly, boolean allowedInGame, boolean plotOnly, boolean allowedInHub) {
        super();
        this.main = main;
        this.plotOnly = plotOnly;
        this.allowedInGame = allowedInGame;
        this.modOnly = modOnly;
        this.minLevelRequire = minLevelRequire;
        this.allowedInHub = allowedInHub;
    }

    public TCommand(CYG main, boolean modOnly, boolean allowedInGame, boolean plotOnly, boolean allowedInHub) {
        this(main, CLevel.LEVEL1, modOnly, allowedInGame, plotOnly, allowedInHub);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;

        if(player.getWorld().getName().equalsIgnoreCase("world") && !allowedInHub)
        {
            player.sendMessage("§cImpossible au /hub");
            return true;
        }

        if(main.getDataManager().getLevel(player.getName()) < minLevelRequire.getRealLevel())
        {
            player.sendMessage("§7[§eGravenMC§7]§r §cVous devez être au minimum §nNiveau "+ minLevelRequire.getRealLevel()+"§r§c pour utiliser cette commande");
            player.sendMessage("§7[§eGravenMC§7]§r Il vous manque donc " + (minLevelRequire.getTrophy() - main.getDataManager().getTrophys(player.getName())) +" trophés");

            return true;
        }

        if(!player.getWorld().getName().contains(player.getUniqueId().toString()) && plotOnly)
        {
            player.sendMessage("§7[§eGravenMC§7]§r §cCommande à faire sur votre parcelle uniquement");
            return true;
        }

        if (this.main.getDataManager().getRank(player).isNotMod() && modOnly && !player.isOp()) {
            player.sendMessage("§7[§eGravenMC§7]§r §cUniquement pour le staff");
            return false;
        }

        if(main.getGameManager().isPlayerInGame(player).size()!=0 && !allowedInGame)
        {
            player.sendMessage("§7[§eGravenMC§7]§r §cCommande impossible durant une partie");
            return true;
        }

        onExecute(player, args);

        return true;
    }

    protected abstract void onExecute(Player player, String[] args);

    protected Location getHubLocation() { return main.spawnLocation; }

    protected ScoreboardManager scoreboardManager(){ return main.getScoreboardManager();}

    protected CYGDataManager dataManager(){ return main.getDataManager();}

    protected CustomMenuManager menuManager(){return main.getMenuManager();}

    protected CYGWorleditManager worldEditManager(){return main.getWorldEditManager();}

    protected CYGameManager gameManager(){
        return main.getGameManager();
    }

    protected Plot getPlot(Player player){ return getPlot(player.getName()); }

    protected Plot getPlot(String name) { return null; }

    protected String getPlotUUID(String name){ return dataManager().getStorages().get(name).getUUID(); }

    protected boolean hasPlot(String pseudo) { return dataManager().getStorages().containsKey(pseudo); }


}
