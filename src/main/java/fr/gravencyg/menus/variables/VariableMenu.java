package fr.gravencyg.menus.variables;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Storage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VariableMenu implements CustomMenu {

    private CYG main;

    public VariableMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Les Variables";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        inv.clear();

        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        int currentPage = main.getMenuManager().getCurrentPage(player);
        int maxItemPerPage = 45;

        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            Map<String, String> variables = playerStorage.getPlot(player.getWorld().getName()).getVariables();
            int numberOfMessages = variables.size();


            // place locations
            int start = currentPage * maxItemPerPage;
            int stop = (currentPage * maxItemPerPage) + maxItemPerPage;

            List<String> key = Arrays.asList(variables.keySet().toArray(new String[0]));

            int count = 0;

            for(int i = start; i < stop; i++){

                if(key.size() <= i) break;

                String k = key.get(i);
                String v = variables.get(k);

                // place beacon item
                ItemStack locationItem = new ItemBuilder(Material.PAPER, 1)
                        .setName(ChatColor.BLUE+ k)
                        .addLoreLine(ChatColor.GRAY + v)
                        .toItemStack();

                inv.setItem(count, locationItem);
                count++;
            }

            // multi page required
            if(numberOfMessages > maxItemPerPage)
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
    }

    @Override
    public int getSize() { return 54; }

}
