package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TeleportAction extends Action {

    private Random random = new Random();

    public TeleportAction(CYG main) {
        super(main, CLevel.LEVEL1);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "Teleport()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Téléporter le joueur à une zone";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess process) {
        Plot plot = main.getDataManager().getPlotFromWorldLocationOf(player);

        if(plot.getLocationsSet().containsKey(LocationUtils.fromLocToString(block.getLocation()))) {

            List<String> locations = new ArrayList<>();
            String locName = plot.getLocationsSet().get(LocationUtils.fromLocToString(block.getLocation()));
            locations.add(locName);

            boolean isRandom = false;

            for(int i = 1; i < 100; i++){
                Location blockToTop = block.getLocation().clone().add(0, i, 0);

                if(blockToTop.getBlock().getType() == Material.COBWEB){
                    isRandom = true;
                    break;
                }

                if(blockToTop.getBlock().getType() != Material.BEACON)
                    break;

                locations.add( plot.getLocationsSet().get(LocationUtils.fromLocToString(blockToTop)));
            }

            if(isRandom)
            {
                String randomLocation = locations.get(random.nextInt(locations.size()));
                teleport(player, randomLocation, plot);
            }
            else
            {

                if(main.getGameManager().isPlayerInGame(player).size() != 0){
                    CYGame game = main.getGameManager().getCurrentGame(player);
                    int playerIndex = game.getPlayerIndex(player);

                    if(locations.size() < playerIndex) {
                        if(locations.contains(playerIndex)) {
                            teleport(player, locations.get(playerIndex), plot);
                        }
                    }
                    else
                    {
                        teleport(player, locations.get(random.nextInt(locations.size())), plot);
                    }

                }

                else{
                    teleport(player, locations.get(0), plot);
                }
            }

        }
        else
        {
            player.sendMessage("§eAucun point de téléportation définit !");
        }

    }

    public void teleport(Player player, String locName, Plot plot) {
        if (plot.getLocations().containsKey(locName)) {
            Location tpPoint = LocationUtils.fromStringToLocComplete(player.getWorld().getName(), plot.getLocations().get(locName));
            player.teleport(tpPoint);
            player.sendMessage("§bTéléportation vers " + locName);
        } else {
            player.sendMessage("§bZone inexisante " + locName);
        }
    }
}
