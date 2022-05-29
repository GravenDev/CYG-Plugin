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
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MonsterSpawnAction extends Action {

    public MonsterSpawnAction(CYG main) {
        super(main, CLevel.LEVEL11);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "SpawnMonster()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Faire apparaitre un monstre";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        Plot plot = main.getDataManager().getPlotFromWorldLocationOf(player);
        Location loc = block.getLocation().clone().add(0, 1, 0);

        if(loc.getBlock().getType() == Material.BEACON){

            if(plot.getLocationsSet().containsKey(LocationUtils.fromLocToString(loc))) {

                String locName = plot.getLocationsSet().get(LocationUtils.fromLocToString(loc));
                Location tpPoint = LocationUtils.fromStringToLocComplete(loc.getWorld().getName(), plot.getLocations().get(locName));

                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                Entity entity = loc.getWorld().spawnEntity(tpPoint, spawner.getSpawnedType());

            }
        }

    }


}
