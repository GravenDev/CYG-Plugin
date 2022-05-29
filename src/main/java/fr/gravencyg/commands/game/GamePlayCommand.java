package fr.gravencyg.commands.game;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.plot.PlayPlayerMenu;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CLevel;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class GamePlayCommand extends TCommand {

    public GamePlayCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0)
        {
            player.sendMessage("Utilise §e/§rplay §7[§epseudo§7] pour jouer à un jeu");
            return;
        }

        if(args.length == 1)
        {
            String targetName = args[0];

            if(!dataManager().getStorages().containsKey(targetName))
            {
                player.sendMessage("Aucun jeu n'existe pour le créateur §e" + targetName);
                return;
            }

            if(main.getTargetPlayMenu().containsKey(player))
            {
                main.getTargetPlayMenu().remove(player);
            }
            main.getTargetPlayMenu().put(player, targetName);
            main.getMenuManager().open(player, PlayPlayerMenu.class);


        }

        if(args.length == 2)
        {
            int slot = 0;
            String sl = args[1];

            String target = args[0];

            if(!dataManager().getStorages().containsKey(target))
            {
                player.sendMessage("Aucun jeu n'existe pour le créateur §e" + target);
                return;
            }

            if(sl != null)
            {
                slot = Integer.parseInt(sl);
            }

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
            int finalSlot = slot;
            Bukkit.getScheduler().runTaskLater(main, () -> {
                main.getGameManager().join(player, target, finalSlot);
            }, 50);


        }
    }
}
