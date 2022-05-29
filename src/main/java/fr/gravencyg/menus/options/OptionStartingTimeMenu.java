package fr.gravencyg.menus.options;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.GameConfig;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OptionStartingTimeMenu implements CustomMenu {

    private CYG main;

    public OptionStartingTimeMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Temps de lancement";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        GameConfig config = playerStorage.getConfig(player.getWorld().getName());

        for(int i : new int[]{ 5, 10, 15, 30, 60 })
        {
            inv.addItem(getItem(i, config));
        }

        inv.setItem(8, new ItemBuilder(Material.ARROW, 1).setName("§9Revenir en arrière").toItemStack());

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(slot == 8)
        {
            player.closeInventory();
            main.getMenuManager().open(player, OptionMenu.class);
        }
        else
        {
            if(current.getType() == Material.LEGACY_WATCH)
            {
                Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());
                plot.getConfig().setAutoStartTime(current.getAmount());
                main.getDataManager().save(player);

                player.sendMessage("§fLe jeu commenceras désormais en §e" + current.getAmount()+"§r secondes" );

                inv.clear();

                contents(player, inv);
            }
        }
    }

    private ItemStack getItem(int i, GameConfig config) {
        ItemBuilder it = new ItemBuilder(Material.LEGACY_WATCH, i, (byte)3)
                .setName("§e" + i + " §fsecondes avant de lancer");

        if(config.getAutoStartTime() == i)
        {
            it.addLoreLine("§a[SELECTED]");
            it.addEnchant(Enchantment.DURABILITY, 1);
        }

        return it.toItemStack();
    }

    @Override
    public int getSize() { return 9; }
}
