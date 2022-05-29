package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class DropItemAction extends Action {

    private Random random = new Random();

    public DropItemAction(CYG main) {
        super(main, CLevel.LEVEL8);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "DropItem()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Jete un item à un endroit specifique";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        Plot plot = main.getDataManager().getPlotFromWorldLocationOf(player);
        Location loc = block.getLocation().clone().add(0, 1, 0);

        if(loc.getBlock().getType() == Material.BEACON){

            if(plot.getLocationsSet().containsKey(LocationUtils.fromLocToString(loc))) {

                String locName = plot.getLocationsSet().get(LocationUtils.fromLocToString(loc));
                Location spawnPoint = LocationUtils.fromStringToLocComplete(loc.getWorld().getName(), plot.getLocations().get(locName));

                Dropper dropper = (Dropper) block.getState();

                for(ItemStack it : dropper.getInventory().getContents()) {
                    if(it == null) continue;

                    spawnPoint.getWorld().dropItem(spawnPoint, it);
                }

            }
        }
    }

}
