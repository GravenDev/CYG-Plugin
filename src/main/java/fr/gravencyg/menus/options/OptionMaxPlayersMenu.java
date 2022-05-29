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

public class OptionMaxPlayersMenu implements CustomMenu {

    private CYG main;

    public OptionMaxPlayersMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Nombre de joueurs max";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        GameConfig config = playerStorage.getConfig(player.getWorld().getName());

        for(int i : new int[]{ 2, 4, 6, 8, 10, 12, 16, 20 })
        {
            inv.addItem(getItem(i, config));
        }

        inv.setItem(26, new ItemBuilder(Material.ARROW, 1).setName("§9Revenir en arrière").toItemStack());

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(slot == 26)
        {
            player.closeInventory();
            main.getMenuManager().open(player, OptionMenu.class);
        }
        else
        {
            if(current.getType() == Material.PLAYER_HEAD)
            {
                Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());
                plot.getConfig().setMaxPlayers(current.getAmount());
                main.getDataManager().save(player);

                player.sendMessage("§fLe nombre maximum de joueurs est désormais de §e" + current.getAmount()+"§r" );

                inv.clear();
                contents(player, inv);
            }
        }
    }

    private ItemStack getItem(int i, GameConfig config) {
        ItemBuilder it = new ItemBuilder(Material.PLAYER_HEAD, i, (byte)3)
            .setName("§e" + i + " §fjoueurs au maximum");

        if(config.getMaxPlayers() == i)
        {
            it.addLoreLine("§a[SELECTED]");
            it.addEnchant(Enchantment.DURABILITY, 1);
        }

        return it.toItemStack();
    }


    @Override
    public int getSize() { return 27; }
}
