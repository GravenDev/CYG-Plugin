package fr.gravencyg.menus.core;

import fr.gravencyg.CYG;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomMenuListener implements Listener {

    private CYG main;

    public CustomMenuListener(CYG main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();

        if(event.getCurrentItem() == null) return;

        this.main.getMenuManager().registeredMenus.values().stream()
                .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, inv, current, event.getSlot());
                    event.setCancelled(true);
                });

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        if(main.getEventBlockCache().containsValue(player));
        {
            main.getEventBlockCache().remove((Player) event.getPlayer());
            main.getMenuManager().backToStart(player);
        }

        if(main.getTargetPlayMenu().containsKey(player));
        {
            main.getTargetPlayMenu().remove(player);
        }
    }

}
