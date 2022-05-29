package fr.gravencyg.menus.locations;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LocationSelectMenu implements CustomMenu {

    private CYG main;

    public LocationSelectMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Choisir une zone";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        inv.clear();

        Storage playerStorage = main.getDataManager().getStorageByUUID(player.getWorld().getName());
        int currentPage = main.getMenuManager().getCurrentPage(player);
        int maxItemPerPage = 45;

        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            Map<String, String> locations = playerStorage.getPlot(player.getWorld().getName()).getLocations();
            int numberOfLocations = locations.size();


            // place locations
            int start = currentPage * maxItemPerPage;
            int stop = (currentPage * maxItemPerPage) + maxItemPerPage;

            List<String> key = Arrays.asList(locations.keySet().toArray(new String[0]));

            int count = 0;

            for(int i = start; i < stop; i++){

                if(key.size() <= i) break;

                String k = key.get(i);
                String v = locations.get(k);
                boolean applyEnchant = false;

                // get selected event name to apply enchantment
                if(main.getEventBlockCache().containsKey(player)) {

                    Block selectedBlockInCache = main.getEventBlockCache().get(player);
                    Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());

                    if(plot.getLocationsSet().containsKey(LocationUtils.fromLocToString(selectedBlockInCache.getLocation())))
                    {
                        if(plot.getLocationsSet().get(LocationUtils.fromLocToString(selectedBlockInCache.getLocation())).equals(v)){
                            applyEnchant = true;
                        }
                    }

                }

                // place beacon item

                inv.setItem(count, getLocationBlock(ChatColor.BLUE+ k, ChatColor.GRAY + LocationUtils.fromStringToSimpleString(player.getUniqueId().toString(), v), applyEnchant));

                count++;
            }

            // multi page required
            if(numberOfLocations > maxItemPerPage)
            {
                if(currentPage != 0) inv.setItem(45, new ItemBuilder(Material.ARROW, 1).setName("§9Revenir à la page " + (currentPage - 1)).toItemStack()); // previous button
                if(key.size() > maxItemPerPage * (currentPage + 1)) inv.setItem(53, new ItemBuilder(Material.ARROW, 1).setName("§9Aller à la page " + (currentPage + 1)).toItemStack()); // next button
            }

        }


    }

    public ItemStack getLocationBlock(String name, String lore, boolean applyEnchant) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.BEACON, 1)
                .setName(name)
                .addLoreLine(lore);

        if(applyEnchant)
            itemBuilder.addEnchant(Enchantment.DURABILITY, 10);

        return itemBuilder.toItemStack();
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

        if(slot == 45 && current.getType() == Material.ARROW)
        {
            inv.clear();
            main.getMenuManager().previousPage(player);
            contents(player, inv);
        }

        if(slot == 53 && current.getType() == Material.ARROW)
        {
            inv.clear();
            main.getMenuManager().nextPage(player);
            contents(player, inv);
        }

        if (current != null && current.getType() == Material.BEACON && current.hasItemMeta() && current.getItemMeta().hasLore()
                && main.getEventBlockCache().containsKey(player))
        {
            Block selectedBlockInCache = main.getEventBlockCache().get(player);

            String name = current.getItemMeta().getDisplayName().replace("§9","");

            main.getDataManager().addLocationSet(player.getWorld().getName(), selectedBlockInCache.getLocation(), name);

            player.sendMessage("§e"+name + "§r définit !");

            player.closeInventory();
        }

    }

    @Override
    public int getSize() { return 54; }

}
