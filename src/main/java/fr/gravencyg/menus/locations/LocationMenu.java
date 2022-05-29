package fr.gravencyg.menus.locations;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.menus.options.OptionMenu;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocationMenu implements CustomMenu {

    private CYG main;

    public LocationMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Les Zones";
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

                // place beacon item
                ItemStack locationItem = new ItemBuilder(Material.BEACON, 1)
                        .setName(ChatColor.BLUE+ k)
                        .addLoreLine(ChatColor.GRAY + LocationUtils.fromStringToSimpleString(player.getUniqueId().toString(), v))
                        .toItemStack();

                inv.setItem(count, locationItem);
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

        if (current != null && current.getType() == Material.BEACON && current.hasItemMeta() && current.getItemMeta().hasLore())
        {
            player.closeInventory();

            ItemMeta iM = current.getItemMeta();
            String lore = iM.getLore().get(0).split("§7")[1];
            Location location = LocationUtils.fromString2toLoc(player.getWorld().getName(), lore);

            player.teleport(location);

        }
    }

    @Override
    public int getSize() { return 54; }

}
