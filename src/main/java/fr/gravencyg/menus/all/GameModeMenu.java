package fr.gravencyg.menus.all;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GameModeMenu implements CustomMenu {

    private CYG main;

    public GameModeMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "GameMode Menu";
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(1, new ItemBuilder(Material.GRASS, 1).setName("§aSurvie").toItemStack());
        inv.setItem(3, new ItemBuilder(Material.DIRT, 1).setName("§aAventure").toItemStack());
        inv.setItem(5, new ItemBuilder(Material.GOLD_BLOCK, 1).setName("§aCréatif").toItemStack());
        inv.setItem(7, new ItemBuilder(Material.DIAMOND_BLOCK, 1).setName("§aSpectateur").toItemStack());
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if (current != null && current.getType() != Material.AIR)
        {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 7, 6);
            switch (current.getType())
            {
                case GRASS: player.setGameMode(GameMode.SURVIVAL); break;
                case DIRT: player.setGameMode(GameMode.ADVENTURE); break;
                case GOLD_BLOCK: player.setGameMode(GameMode.CREATIVE); break;
                case DIAMOND_BLOCK: player.setGameMode(GameMode.SPECTATOR); break;

                default: break;
            }
        }
    }

    @Override
    public int getSize() { return 9; }
}
