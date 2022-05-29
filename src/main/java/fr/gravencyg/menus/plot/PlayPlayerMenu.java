package fr.gravencyg.menus.plot;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.menus.core.CustomMenu;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayPlayerMenu implements CustomMenu {

    private CYG main;

    public PlayPlayerMenu(CYG main) {
        this.main = main;
    }

    @Override
    public String name() {
        return "Jouer à un jeu";
    }

    @Override
    public void contents(Player player, Inventory inv) {
        String target = main.getTargetPlayMenu().get(player);
        Storage storage = main.getDataManager().getStorages().get(target);
        List<Plot> plots = storage.getPlots();
        int i = 0;

        for(Plot plot : plots)
        {
            inv.setItem(i, new ItemBuilder(plot.getVerifyConfig().getMaterial())
                    .setName(main.getPlotManager().getGameNameFromPlot(player, i))
                    .addLoreLine("§e>> Clique pour rejoindre <<").toItemStack());
            i++;
        }

        player.sendMessage("Ouverture du menu de jeu de §e" + target);
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current.getType() != Material.AIR && current.hasItemMeta())
        {
            String target = main.getTargetPlayMenu().get(player);
            Storage storage = main.getDataManager().getStorages().get(target);
            Plot targetPlot = storage.getPlots().get(slot);

            if(main.getGameManager().isPlayerInGame(player).size() != 0) {
                main.getGameManager().quit(player);
            }

            if(targetPlot.getConfig().isWhitelist() && !targetPlot.getFriends().contains(player.getName()) && !player.getName().equalsIgnoreCase(storage.getDisplayName()))
            {
                player.sendMessage("Le jeu est actuellement en §cWhitelist");
                return;
            }

            if(!main.getGameManager().hasPendingGame(target, slot))
            {
                main.getGameManager().create(target, slot);
                player.sendMessage("§7>>§r Création d'une partie pour le jeu de §e"+ target);
            }

            player.sendMessage("§7>>§r Vous avez rejoint \""+targetPlot.getName()+"\" par§e "+ target);

            WorldCreator worldCreator = new WorldCreator(storage.getUUID()+"#" + slot);
            worldCreator.type(WorldType.FLAT);

            World playerWorld = Bukkit.createWorld(worldCreator);

            // wait 3 seconds before teleport the player
            Bukkit.getScheduler().runTaskLater(main, () -> {
                main.getGameManager().join(player, target, slot);
            }, 50);


            player.closeInventory();
        }
    }

    @Override
    public int getSize() { return 9; }
}
