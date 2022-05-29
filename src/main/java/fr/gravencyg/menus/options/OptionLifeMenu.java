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

public class OptionLifeMenu implements CustomMenu {

    private CYG main;

    public OptionLifeMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Nombre de vies";
    }

    @Override
    public void contents(Player player, Inventory inv) {


        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        GameConfig config = playerStorage.getConfig(player.getWorld().getName());

        for(int i : new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 16, 24})
        {
            inv.addItem(getItem(i, config));
        }

        inv.setItem(26, new ItemBuilder(Material.ARROW, 1).setName("§9Revenir en arrière").toItemStack());

    }

    private ItemStack getItem(int i, GameConfig config) {
        ItemBuilder it = new ItemBuilder(Material.POTION, i)
                .setName("§e" + i + " §fvie(s) avant elimination");

        if(config.getLifes() == i)
        {
            it.addLoreLine("§a[SELECTED]");
            it.addEnchant(Enchantment.DURABILITY, 1);
        }

        return it.toItemStack();
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
            if(current.getType() == Material.POTION)
            {
                Plot plot = main.getDataManager().getStorages().get(player.getName()).getPlot(player.getWorld().getName());
                plot.getConfig().setLifes(current.getAmount());
                main.getDataManager().save(player);

                player.sendMessage("§fChaque joueur aura §c" + current.getAmount()+"§r vies sur le jeu avant d'etre éliminé !" );

                inv.clear();

                contents(player, inv);
            }
        }
    }

    @Override
    public int getSize() { return 27; }
}
