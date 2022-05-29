package fr.gravencyg.menus.all;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.model.VerifyGameConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayMenu implements CustomMenu {

    private CYG main;

    public PlayMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Jouer";
    }

    @Override
    public void contents(Player player, Inventory inv) {
        for(Map.Entry<Integer, Storage> st: main.getGameManager().getVerifyGames().entrySet())
        {
            Storage storage = st.getValue();
            int id = 0;
            for(Plot plot : storage.getPlots())
            {
                VerifyGameConfig verifyGameConfig = plot.getVerifyConfig();
                String state = "En Attente";

                if(verifyGameConfig.isVerify()) {

                    inv.setItem(verifyGameConfig.getSlot(),
                            new ItemBuilder(verifyGameConfig.getMaterial(), 1)
                                    .setName("§e" + plot.getName() + "§7 by " + storage.getDisplayName())
                                    .addLoreLine("§eStatus : " + state)
                                    .addLoreLine("/play " + storage.getDisplayName() + " " + id)
                                    .toItemStack()
                    );
                }
                id ++;
            }
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(main.getGameManager().getVerifyGames().containsKey(slot))
        {
            player.closeInventory();
            player.chat(current.getItemMeta().getLore().get(1));
        }
    }

    @Override
    public int getSize() { return 54; }
}
