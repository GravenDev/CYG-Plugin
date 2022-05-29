package fr.gravencyg.manager;

import fr.gravencyg.CYG;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.CRank;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class CYGPlotManager {

    private CYG main;
    private CYGDataManager dataManager;

    public CYGPlotManager(CYG main){
        this.main = main;
        this.dataManager = main.getDataManager();
    }

    public void createPlot(Player player, int index) {
        List<Plot> playerPlot = getPlotsByPlayer(player);
        int maxPlotPerPlayer = 3;

        if(playerPlot.size() < maxPlotPerPlayer) {
            getStorageByPlayer(player).createPlot(player);

            player.sendMessage(ChatColor.GOLD + "Création d'une parcelle...");
            createWorld(player, index);

            dataManager.save(player);
        }
        else{
            player.sendMessage(ChatColor.GOLD + "Vous ne pouvez pas créer plus de §e" + maxPlotPerPlayer + "§r parcelles..");

        }
    }

    public void createWorld(Player player, int index){
        WorldCreator worldCreator = new WorldCreator(player.getUniqueId().toString() + "_" + index);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);

        World playerWorld = Bukkit.createWorld(worldCreator);
        WorldBorder worldBorder = playerWorld.getWorldBorder();
        int borderSize = main.getDataManager().getBorderSize(player);

        if (borderSize != worldBorder.getSize() && !player.isOp()) {
            worldBorder.setCenter(new Location(playerWorld, 0, 0, 0));
            worldBorder.setSize(borderSize);
        }

        teleportPlayer(player, player.getUniqueId().toString(), index);
    }

    public boolean isPlotExist(String ownerUUID, int index){
        return getStorageByUUID(ownerUUID).getPlots().size() != index;
    }

    public void teleportPlayer(Player player, String ownerPlotUUId, int index) {
        player.sendMessage("§eTéléportation vers le plot n°" + (index+1));

        WorldCreator worldCreator = new WorldCreator(player.getUniqueId().toString() + "_" + index);
        World playerWorld = Bukkit.createWorld(worldCreator);
        worldCreator.type(WorldType.FLAT);
        worldCreator.generateStructures(false);

        Bukkit.getScheduler().runTaskLater(main, () -> {
            player.teleport(new Location(playerWorld, 0, 20, 0));
            player.setGameMode(GameMode.CREATIVE);

            // apply worldborder
            WorldBorder worldBorder = player.getWorld().getWorldBorder();
            int borderSize = main.getDataManager().getBorderSize(player);

            if (borderSize != worldBorder.getSize()) {
                worldBorder.setCenter(new Location(player.getWorld(), 0, 0, 0));
                worldBorder.setSize(borderSize);
            }

        }, 100);
    }

    public Storage getStorageByUUID(String name) {
        for(Map.Entry<String, Storage> storage : dataManager.getStorages().entrySet())
        {
            if (name.contains(storage.getValue().getUUID()))
            {
                return storage.getValue();
            }
        }
        return new Storage();
    }

    public Storage getStorageByPlayer(Player player){
        return dataManager.getStorages().get(player.getName());
    }

    public List<Plot> getPlotsByPlayer(Player player){
        return getStorageByPlayer(player).getPlots();
    }

    public String getGameNameFromPlot(Player player, int i) {
        if(getStorageByUUID(player.getUniqueId().toString()).getPlots().size() -1 >= i)
        {
            return getStorageByUUID(player.getUniqueId().toString()).getPlots().get(i).getName();
        }
        return "§7Aucun jeu pour le moment";
    }
}
