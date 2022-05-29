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

public class OptionAutostartMenu implements CustomMenu {

    private CYG main;

    public OptionAutostartMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Minimum de joueurs";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        GameConfig config = playerStorage.getConfig(player.getWorld().getName());

        for(int i : new int[]{ 1, 2, 4, 8, 12, 16, 20 })
        {
            inv.addItem(getItem(i, config));
        }

        inv.setItem(8, new ItemBuilder(Material.ARROW, 1).setName("§9Revenir en arrière").toItemStack());

    }

    private ItemStack getItem(int i, GameConfig config) {
        ItemBuilder it = new ItemBuilder(Material.REDSTONE, i, (byte)3)
                .setName("§e" + i + " §fjoueurs pour commencer");

        if(config.getAutoStartPlayer() == i)
        {
            it.addLoreLine("§a[SELECTED]");
            it.addEnchant(Enchantment.DURABILITY, 1);
        }

        return it.toItemStack();
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
            if(current.getType() == Material.REDSTONE)
            {
                Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());
                plot.getConfig().setAutoStartPlayer(current.getAmount());
                main.getDataManager().save(player);

                player.sendMessage("§fLe jeu commenceras désormais à §e" + current.getAmount()+"§r joueurs" );

                inv.clear();

                contents(player, inv);
            }
        }
    }

    @Override
    public int getSize() { return 9; }
}
