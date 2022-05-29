package fr.gravencyg.menus.all;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.manager.CYGHologramManager;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CEvent;
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

import java.util.Map;

public class EventMenu implements CustomMenu {

    private CYG main;

    public EventMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Choisir un évenement";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        int i = 0;

        for(CEvent event : CEvent.values()) {

            boolean applyMetaSkull = false;
            boolean applyEnchant = false;

            // get selected event name to apply enchantment
            if(main.getEventBlockCache().containsKey(player)) {
                Plot plot = main.getDataManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());
                if(plot.getEvents().containsKey(event.getEventName()))
                {
                    Block selectedBlockInCache = main.getEventBlockCache().get(player);

                    if(plot.getEvents().get(event.getEventName()).equals(LocationUtils.fromLocToString(selectedBlockInCache.getLocation()))){
                        applyEnchant = true;
                    }
                }

            }

            if(i == 0)  applyMetaSkull = true;

            inv.setItem(i, getEventBlock(event.getMaterial(), event.getEventName(), event.getDescription(), applyEnchant, applyMetaSkull));

            i += 1;
        }

        inv.setItem(26, new ItemBuilder(Material.COMPASS)
                .setName(ChatColor.BLUE + "Démarrer le code")
                .addLoreLine(ChatColor.GRAY + "Cliquez ici pour tester votre code").toItemStack());


    }

    public ItemStack getEventBlock(Material material, String eventName, String description, boolean applyEnchant, boolean applyMetaSkull) {

        byte data = 0;

        if(applyMetaSkull) data = 3;

        ItemBuilder itemBuilder = new ItemBuilder(material, 1, data)
                .setName(ChatColor.BLUE + eventName + "()")
                .addLoreLine(ChatColor.GRAY + description);

        if(applyEnchant)
            itemBuilder.addEnchant(Enchantment.DURABILITY, 10);

        return itemBuilder.toItemStack();
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if (current != null && main.getEventBlockCache().containsKey(player))
        {
            Block selectedBlockInCache = main.getEventBlockCache().get(player);

            if(current.getType() == null) return;

            if(slot < CEvent.values().length) {

                player.closeInventory();

                CEvent event = CEvent.values()[slot];
                Storage storage = main.getDataManager().getStorageByUUID(player.getWorld().getName());
                Plot plot = storage.getPlot(player.getWorld().getName());

                // event already exist
                if(plot.getEvents().containsValue(LocationUtils.fromLocToString(selectedBlockInCache.getLocation())));
                {
                    for(Map.Entry<String, String> e: plot.getEvents().entrySet())
                    {
                        if(e.getValue().equals(LocationUtils.fromLocToString(selectedBlockInCache.getLocation())))
                        {
                            main.getDataManager().removeEvent(player, player.getWorld().getName(), selectedBlockInCache);
                        }
                    }
                }

                // event already exist
                if(plot.getEvents().containsKey(event.getEventName()))
                {
                    String eve = plot.getEvents().get(event.getEventName());
                    Block block = LocationUtils.fromStringToLoc(storage.getUUID(), eve).getBlock();
                    main.getDataManager().removeEvent(player, block);
                    main.getDataManager().save(player);

                    CYGHologramManager hologramManager = main.getHologramManager();
                    hologramManager.removeHologram(block);
                }

                player.sendMessage(ChatColor.GRAY + "Evenement " + ChatColor.BLUE + event.getEventName() + ChatColor.GRAY + " placé !");
                updateEvent(player, event.getEventName(), selectedBlockInCache.getLocation());

            }

            if(current.getType() == Material.COMPASS)
            {
                player.closeInventory();

                main.getCompilerManager().runEventByBlock(player, player.getWorld().getName(), selectedBlockInCache);
            }

        }
    }

    private void updateEvent(Player player, String event, Location blockLocation) {
        main.getDataManager().updateEvent(player, event, LocationUtils.fromLocToString(blockLocation));

        CYGHologramManager hologramManager = main.getHologramManager();
        hologramManager.removeHologram(blockLocation.getBlock());
        hologramManager.registerEventHologram(blockLocation, event);
    }

    @Override
    public int getSize() { return 27; }
}
