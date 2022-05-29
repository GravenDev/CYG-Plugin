package fr.gravencyg.menus.plot;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.all.ActionMenu;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.menus.options.OptionMenu;
import fr.gravencyg.model.GameConfig;
import fr.gravencyg.model.Storage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlotManageMenu implements CustomMenu {

    private CYG main;

    public PlotManageMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Gestion du Plot";
    }

    @Override
    public void contents(Player player, Inventory inv) {


        inv.setItem(11, new ItemBuilder(Material.NAME_TAG, 1)
                .setName("§eRéglages§7")
                .addLoreLine("§eConfigurer son jeu")
                .addLoreLine("§fRaccourci §9/options")
                .toItemStack());

        inv.setItem(12, new ItemBuilder(Material.CHEST, 1)
                .setName("§eActions§7")
                .addLoreLine("§eObtenir des blocs de code")
                .addLoreLine("§fRaccourci §9/actions ou /ac")
                .toItemStack());

        inv.setItem(13, new ItemBuilder(Material.BEACON, 1)
                .setName("§eZone  de Spawn§7")
                .addLoreLine("§eTéléportation")
                .toItemStack());

        inv.setItem(14, new ItemBuilder(Material.HOPPER, 1)
                .setName("§eZone du code§7")
                .addLoreLine("§eTéléportation")
                .toItemStack());

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

        Storage storage = main.getPlotManager().getStorageByUUID(player.getWorld().getName());
        String uuid = player.getWorld().getName();

        switch (current.getType())
        {
            case NAME_TAG:
                player.closeInventory();
                main.getMenuManager().open(player, OptionMenu.class);
                break;

            case CHEST:
                player.closeInventory();
                main.getMenuManager().open(player, ActionMenu.class);
                break;

            case BEACON:
                player.teleport(storage.getPlot(uuid).getSpawn(uuid));
                player.sendMessage("§eTéléportation vers le spawn...");
                break;

            case HOPPER:
                player.teleport(storage.getPlot(uuid).getCode(uuid));
                player.sendMessage("§9Téléportation vers le code...");
                break;
        }

    }

    @Override
    public int getSize() { return 45; }
}
