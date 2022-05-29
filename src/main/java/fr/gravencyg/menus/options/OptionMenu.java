package fr.gravencyg.menus.options;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.GameConfig;
import fr.gravencyg.model.Storage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OptionMenu implements CustomMenu {

    private CYG main;

    public OptionMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Configurer son jeu";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        Storage playerStorage = main.getDataManager().getStorages().get(player.getName());
        GameConfig config = playerStorage.getConfig(player.getWorld().getName());

        inv.setItem(10, new ItemBuilder(Material.PAPER, 1)
                .setName("Nom Du Jeu§7")
                .addLoreLine("§e"+config.getGameName())
                .toItemStack());

        inv.setItem(11, new ItemBuilder(Material.REDSTONE, config.getAutoStartPlayer())
                .setName("§fDémarrage automatique§7")
                .addLoreLine("§e"+config.getAutoStartPlayer() + "§f Joueur(s)")
                .toItemStack());

        inv.setItem(12, new ItemBuilder(Material.PLAYER_HEAD, config.getMaxPlayers(),  (byte)3)
                .setName("§fMaximum de joueurs")
                .addLoreLine("§e" + config.getMaxPlayers() + "§f Joueurs")
                .toItemStack());

        inv.setItem(13, new ItemBuilder(Material.LEGACY_WATCH, config.getAutoStartTime())
                .setName("§fDurée de demarrage§7")
                .addLoreLine("§e"+config.getAutoStartTime() + "§f secondes")
                .toItemStack());

        inv.setItem(14, new ItemBuilder(Material.REDSTONE_BLOCK, 1)
                .setName("§fWhitelist§7")
                .addLoreLine("§e" + config.isWhitelist())
                .toItemStack());

        inv.setItem(15, new ItemBuilder(Material.POTION, config.getLifes())
                .setName("§fNombre de vies§7")
                .addLoreLine("§e" + config.getLifes()+"§e vies par joueur")
                .toItemStack());

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        switch (current.getType())
        {
            case PAPER:
                player.closeInventory();
                player.chat("/plot gamename");
                break;
            case REDSTONE:
                player.closeInventory();
                main.getMenuManager().open(player, OptionAutostartMenu.class);
                break;

            case PLAYER_HEAD:
                player.closeInventory();
                main.getMenuManager().open(player, OptionMaxPlayersMenu.class);
                break;

            case LEGACY_WATCH:
                player.closeInventory();
                main.getMenuManager().open(player, OptionStartingTimeMenu.class);
                break;

            case REDSTONE_BLOCK:
                player.closeInventory();
                main.getMenuManager().open(player, OptionWhitelistMenu.class);
                break;

            case POTION:
                player.closeInventory();
                main.getMenuManager().open(player, OptionLifeMenu.class);
                break;

            default:
                break;
        }
    }

    @Override
    public int getSize() { return 27; }
}
