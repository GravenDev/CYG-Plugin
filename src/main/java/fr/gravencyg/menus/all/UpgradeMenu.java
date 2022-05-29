package fr.gravencyg.menus.all;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.PlotSize;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeMenu implements CustomMenu {

    private CYG main;

    public UpgradeMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Améliorer";
    }

    @Override
    public void contents(Player player, Inventory inv) {

        int[] graySlot = {9, 10, 11, 12, 13, 14, 15, 16, 28, 29, 30, 31, 32, 33, 34, 35, 45, 46, 47, 48, 49, 50, 51, 52};
        int[] level = {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 25, 24, 23, 22, 21, 20, 19, 18, 27, 36, 37, 38, 39, 40, 41, 42, 43, 44, 53};

        for(int gray : graySlot)
        {
            inv.setItem(gray, new ItemBuilder(Material.BLACK_STAINED_GLASS, 1, (byte)7).setName(" ").toItemStack());
        }

        int count = 1;

        int playerLevel = main.getDataManager().getLevel(player.getName());

        for(int lvl : level)
        {
            Material material;
            String name = "";
            byte data = 0;

            if(count <= playerLevel)
            {
                material = Material.GREEN_STAINED_GLASS;
                name = "Niveau §9" + count + " §7[§eUNLOCKED§7]";
                data = 13;
            }
            else
            {
                name = "Niveau §9" + count + " §7[§cLOCKED§7]";
                material = Material.COAL_BLOCK;
            }

            ItemBuilder item = new ItemBuilder(material, count, data).setName(name);
            CLevel completeLevel = CLevel.values()[count-1];
            PlotSize plotSize = completeLevel.getPlotSize();

            item.addLoreLine("");

            if(plotSize != PlotSize.NONE) {
                item.addLoreLine("§eParcelle " + plotSize.getName() + " : " + plotSize.getBorderSize() + " x " + plotSize.getBorderSize());
                item.addLoreLine("");
            }

            if(completeLevel.getCommands().size() != 0) {
                item.addLoreLine("§fLes Commandes:");
                item.addLoreLine("");
                for (String lore : completeLevel.getCommands()) {
                    item.addLoreLine("§f" + lore);
                }
            }

            if(completeLevel.getLoreActions().size() != 0) {
                item.addLoreLine("");
                item.addLoreLine("§fLes Blocs:");
                item.addLoreLine("");

                for (String action : completeLevel.getLoreActions()) {
                    item.addLoreLine("§f" + action);
                }
            }

            item.addLoreLine("");
            item.addLoreLine("§f>> "+ completeLevel.getTrophy() +" §etrophés requis §f<<");

            inv.setItem(lvl, item.toItemStack());
            count += 1;
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
       if(current.getType() == Material.COAL_BLOCK)
       {
           player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 2, 7);
       }
        if(current.getType() == Material.BLACK_STAINED_GLASS)
        {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 2, 7);
        }
    }

    @Override
    public int getSize() { return 54; }
}
