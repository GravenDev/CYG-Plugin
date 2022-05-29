package fr.gravencyg.actions.inventory;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChestAction extends Action {

    private Random random = new Random();

    public ChestAction(CYG main) {
        super(main, CLevel.LEVEL1);
    }

    @Override
    public String title() {
        return ChatColor.BLUE +"Stuff()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY +"Offrir du stuff à un joueur";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        Block top = block.getLocation().clone().add(0, 1, 0).getBlock();

        if(top.getType() == Material.BEACON){

            for(int i = 1; i < 100; i++){
                Location blockToTop = block.getLocation().clone().add(0, i, 0);

                if(blockToTop.getBlock().getType() != Material.BEACON) break;

                refillChest(player, block, blockToTop.getBlock());
            }

        }

        else{
            giveStuff(player, block);
        }


    }

    public void giveStuff(Player player, Block block){

        Chest chest = (Chest) block.getState();

        List<ItemStack> items = new ArrayList<>();

        for (ItemStack it : chest.getInventory().getContents())
        {
            if (it == null) continue;
            items.add(it);
        }

        boolean isRandom = false;
        int randomNbItems = 0;

        for(int i = 1; i < 100; i++){
            Location blockToTop = block.getLocation().clone().add(0, i, 0);

            if(blockToTop.getBlock().getType() == Material.AIR)
                break;

            isRandom = true;
            randomNbItems += 1;
        }

        if(isRandom)
        {
            for(int i = 0; i < randomNbItems; i++)
            {
                ItemStack randomItem = items.get(random.nextInt(items.size()));
                main.getArmorManager().addItem(player, randomItem);
            }
        }

        else {
            for (ItemStack item : items) {
                if (item == null) continue;

                main.getArmorManager().addItem(player, item);
            }
        }
    }

    public void refillChest(Player player, Block block, Block tpBlock){

        Chest chest = (Chest) block.getState();
        Plot plot = main.getDataManager().getStorageByUUID(block.getWorld().getName()).getPlot(player.getWorld().getName());

        if(plot.getLocationsSet().containsKey(LocationUtils.fromLocToString(tpBlock.getLocation()))) {

            String locName = plot.getLocationsSet().get(LocationUtils.fromLocToString(tpBlock.getLocation()));

            if(plot.getLocations().containsKey(locName))
            {
                player.sendMessage("§bRemplissage du coffre " + locName);

                Location location = LocationUtils.fromStringToLoc(block.getWorld().getName(), plot.getLocations().get(locName));

                if(location.getBlock().getType() == Material.CHEST)
                {
                    Chest chestTT = (Chest) location.getBlock().getState();
                    for(ItemStack it : chest.getInventory().getContents()){
                        if(it == null) continue;

                        chestTT.getInventory().addItem(it);
                    }

                }
                else{
                    player.sendMessage("Aucun coffre n'a été trouvé en §9" + LocationUtils.fromLocToString(tpBlock.getLocation()));
                }
            }

        }
        else{
            player.sendMessage("Aucun coffre n'a été trouvé en §9" + LocationUtils.fromLocToString(tpBlock.getLocation()));
        }

    }



}
