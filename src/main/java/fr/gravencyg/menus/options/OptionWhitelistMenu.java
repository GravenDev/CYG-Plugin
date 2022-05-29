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

public class OptionWhitelistMenu implements CustomMenu {

    private CYG main;

    public OptionWhitelistMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Whitelist";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        GameConfig config = playerStorage.getConfig(player.getWorld().getName());
        inv.setItem(3, new ItemBuilder(Material.REDSTONE_BLOCK, 1).setName("§aActiver la Whitelist").addLoreLine("§7Seulement les amis peuvent rejoindre").toItemStack());
        inv.setItem(5, new ItemBuilder(Material.COAL_BLOCK, 1).setName("§cDésactiver la Whitelist").addLoreLine("§7Tout le monde peut rejoindre").toItemStack());

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
            if(current.getType() == Material.REDSTONE_BLOCK)
            {
                Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());
                plot.getConfig().setWhitelist(true);
                main.getDataManager().save(player);

                player.sendMessage("§fWhitelist §aActivé");
            }

            if(current.getType() == Material.COAL_BLOCK)
            {
                Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());
                plot.getConfig().setWhitelist(false);
                main.getDataManager().save(player);

                player.sendMessage("§fWhitelist §cDésactivé");

                contents(player, inv);
            }
        }
    }

    @Override
    public int getSize() { return 9; }
}
