package fr.gravencyg.menus.messages;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MessageSelectMenu implements CustomMenu {

    private CYG main;

    public MessageSelectMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Choisir un message";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        inv.clear();

        Storage playerStorage = main.getDataManager().getStorageByUUID(player.getWorld().getName());
        int currentPage = main.getMenuManager().getCurrentPage(player);
        int maxItemPerPage = 45;

        if(playerStorage.getPlot(player.getWorld().getName()) != null) {

            Map<String, String> messages = playerStorage.getPlot(player.getWorld().getName()).getMessages();
            int numberOfMessages = messages.size();

            // place locations
            int start = currentPage * maxItemPerPage;
            int stop = (currentPage * maxItemPerPage) + maxItemPerPage;

            List<String> key = Arrays.asList(messages.keySet().toArray(new String[0]));

            int count = 0;

            for(int i = start; i < stop; i++){

                if(key.size() <= i) break;

                String k = key.get(i);
                String v = messages.get(k).replace("&"," §");
                boolean applyEnchant = false;

                // get selected event name to apply enchantment
                if(main.getEventBlockCache().containsKey(player)) {

                    Block selectedBlockInCache = main.getEventBlockCache().get(player);
                    Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());

                    if(plot.getMessageSet().containsKey(LocationUtils.fromLocToString(selectedBlockInCache.getLocation())))
                    {
                        if(plot.getMessageSet().get(LocationUtils.fromLocToString(selectedBlockInCache.getLocation())).equals(v)){
                            applyEnchant = true;
                        }
                    }

                }

                // place beacon item
                inv.setItem(count, getMessagesBlock(ChatColor.BLUE+ k, ChatColor.GRAY+ v, applyEnchant));

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

    public ItemStack getMessagesBlock(String name, String lore, boolean applyEnchant) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.PAPER, 1)
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

        if (current != null && current.getType() == Material.PAPER && current.hasItemMeta() && current.getItemMeta().hasLore()
        && main.getEventBlockCache().containsKey(player))
        {

            Block selectedBlockInCache = main.getEventBlockCache().get(player);

            String name = current.getItemMeta().getDisplayName().replace("§9","");

            main.getDataManager().addMessageSet(player.getWorld().getName(), selectedBlockInCache.getLocation(), name);

            player.sendMessage("§e"+name + "§r définit !");

            player.closeInventory();
        }

    }

    @Override
    public int getSize() { return 54; }

}
