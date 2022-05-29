package fr.gravencyg.menus.all;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ActionMenu implements CustomMenu {

    private CYG main;

    public ActionMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Actions Menu";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        inv.setItem(0, new ItemBuilder(Material.LAPIS_BLOCK).setName(ChatColor.BLUE+"onEvent()")
                .addLoreLine("§7Declenche un évenement durant le jeu").toItemStack());

        int count = 1;

       for(Map.Entry<Material, Action> action : main.getActionBlocks().getActions().entrySet()) {

           Material material = action.getKey();

           if(material == Material.BREWING_STAND) material = Material.BREWING_STAND;

           if(action.getValue().getRequireLevel().getRealLevel() > main.getDataManager().getLevel(player.getName()))
           {
               inv.setItem(action.getValue().getSlot(), new ItemBuilder(Material.COAL_BLOCK)
                       .setName("§c" + action.getValue().title())
                       .addLoreLine(action.getValue().lore())
                       .addLoreLine("§cVous devez être §nniveau "+action.getValue().getRequireLevel().getRealLevel()+"§r§c !")
                       .toItemStack());
           }
           else {

               inv.setItem(action.getValue().getSlot(), new ItemBuilder(material)
                       .setName(action.getValue().title())
                       .addLoreLine(action.getValue().lore())
                       .toItemStack());
           }

            count += 1;
        }

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if (current != null && current.getType() != Material.AIR)
        {

            if(current.getType() == Material.COAL_BLOCK)
            {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 3, 8);
                return;
            }

            if(current.getType() == Material.WHITE_WOOL)
            {
                player.getInventory().addItem(new ItemBuilder(Material.BLUE_WOOL, 1, (byte)11).setName("§9Equipe Bleu").toItemStack());
                player.getInventory().addItem(new ItemBuilder(Material.RED_WOOL, 1, (byte)14).setName("§cEquipe Rouge").toItemStack());
                return;
            }

            if(current.hasItemMeta() && current.getItemMeta().hasLore()) {

                player.getInventory().addItem(new ItemBuilder(current.getType(), 1)
                        .setName(current.getItemMeta().getDisplayName()).toItemStack());
            }
        }
    }

    @Override
    public int getSize() { return 54; }
}
