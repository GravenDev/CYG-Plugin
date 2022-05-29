package fr.gravencyg.menus;

import fr.gravencyg.CYG;
import fr.gravencyg.menus.all.*;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.menus.locations.LocationMenu;
import fr.gravencyg.menus.locations.LocationSelectMenu;
import fr.gravencyg.menus.messages.MessageMenu;
import fr.gravencyg.menus.messages.MessageSelectMenu;
import fr.gravencyg.menus.options.*;
import fr.gravencyg.menus.plot.PlayPlayerMenu;
import fr.gravencyg.menus.plot.PlotManageMenu;
import fr.gravencyg.menus.plot.PlotMenu;
import fr.gravencyg.menus.variables.VariableMenu;
import fr.gravencyg.menus.variables.VariableSelectMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class CustomMenuManager {

    public Map<Class<? extends CustomMenu>, CustomMenu> registeredMenus = new HashMap<>();

    private Map<Player, Integer> inventoryPageSystem = new HashMap<>();

    public CustomMenuManager(CYG main) {
        // registerer Inventories
        addMenu(new ActionMenu(main));
        addMenu(new EventMenu(main));
        addMenu(new LocationSelectMenu(main));
        addMenu(new LocationMenu(main));
        addMenu(new MessageMenu(main));
        addMenu(new PlayMenu(main));
        addMenu(new MessageSelectMenu(main));
        addMenu(new UpgradeMenu(main));

        addMenu(new OptionMenu(main));
        addMenu(new OptionAutostartMenu(main));
        addMenu(new OptionStartingTimeMenu(main));
        addMenu(new OptionWhitelistMenu(main));
        addMenu(new OptionMaxPlayersMenu(main));
        addMenu(new OptionLifeMenu(main));

        addMenu(new GameModeMenu(main));
        addMenu(new VariableMenu(main));
        addMenu(new VariableSelectMenu(main));
        addMenu(new PlotMenu(main));
        addMenu(new PlotManageMenu(main));
        addMenu(new PlotMenu(main));
        addMenu(new PlayPlayerMenu(main));

    }

    public void addMenu(CustomMenu m){
        this.registeredMenus.put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends CustomMenu> gClass){

        if(!this.registeredMenus.containsKey(gClass)) return;

        if(!inventoryPageSystem.containsKey(player)) {
            inventoryPageSystem.put(player, 0);
        }

        CustomMenu menu = this.registeredMenus.get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);
        player.openInventory(inv);

    }

    public void nextPage(Player player){
        if(inventoryPageSystem.containsKey(player)) {
            inventoryPageSystem.put(player, inventoryPageSystem.get(player) + 1);
        }
    }

    public void previousPage(Player player){
        if(inventoryPageSystem.containsKey(player)) {
            inventoryPageSystem.put(player, inventoryPageSystem.get(player) - 1);
        }
    }

    public int getCurrentPage(Player player){
        if(inventoryPageSystem.containsKey(player)) {
            return inventoryPageSystem.get(player);
        }
        return 0;
    }

    public void backToStart(Player player) {
        if(inventoryPageSystem.containsKey(player)) {
            inventoryPageSystem.remove(player);
        }
    }

}
