package fr.gravencyg.menus.plot;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Storage;
import fr.gravencyg.model.VerifyGameConfig;
import fr.gravencyg.utils.CRank;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlotMenu implements CustomMenu {

    private CYG main;

    public PlotMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Plot";
    }

    @Override
    public void contents(Player player, Inventory inv) {
        int maxPlotPerPlayer = main.getDataManager().getMaxPlotPerPlayer(player);

        for(int i = 0; i < 3; i++)
        {
            if(maxPlotPerPlayer > i)
                inv.setItem(i, new ItemBuilder(Material.GRASS_BLOCK).setName("§eParcelle "+(i+1)+" §r(§aDisponible§r)")
                        .addLoreLine("§fNom du jeu")
                        .addLoreLine("§e" + main.getPlotManager().getGameNameFromPlot(player, i))
                        .addLoreLine("§d")
                        .addLoreLine("§e>> Clique pour rejoindre <<").toItemStack());
            else if(i == 1 ) {
                inv.setItem(i, new ItemBuilder(Material.COAL_BLOCK).setName("§eParcelle " + (i + 1) + " §r(§cIndisponible§r)")
                        .addLoreLine("§c>> Débloqué avec le niveau 10 <<").toItemStack());
            }
            else{
                inv.setItem(i, new ItemBuilder(Material.COAL_BLOCK).setName("§eParcelle " + (i + 1) + " §r(§cIndisponible§r)").addLoreLine("§c>> Débloqué avec le grade Premium <<").toItemStack());
            }
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current.getType() == Material.GRASS_BLOCK && current.hasItemMeta())
        {
            player.closeInventory();

            if(main.getPlotManager().isPlotExist(player.getUniqueId().toString(), slot))
            {
                main.getPlotManager().teleportPlayer(player, player.getUniqueId().toString(), slot);
            }
            else{
                main.getPlotManager().createPlot(player, slot);
            }
        }
    }

    @Override
    public int getSize() { return 9; }
}
