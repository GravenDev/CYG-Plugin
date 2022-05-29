package fr.gravencyg.commands.shortcut;

import fr.gravencyg.CYG;
import fr.gravencyg.manager.CYGDataManager;
import fr.gravencyg.menus.all.ActionMenu;
import fr.gravencyg.menus.options.OptionMenu;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.CRank;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopPremiumCommand implements CommandExecutor {

    private CYG main;

    public ShopPremiumCommand(CYG main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!sender.isOp())
        {
            return false;
        }

        if(args.length < 2)
        {
            sender.sendMessage("/premium add [pseudo]");
            return false;
        }

        String targetName = args[1];

        // usage -> /premium add player
        // usage -> /premium remove player
        if(args[0].equalsIgnoreCase("add"))
        {

            if(main.getDataManager().getStorages().containsKey(targetName))
            {
                Bukkit.getServer().broadcastMessage("§7§e[Boutique]§7 "+targetName+" viens d'acheter le grade §ePremium !");
                CYGDataManager dataManager = main.getDataManager();

                if (dataManager.getLevel(targetName) < 15) {
                    dataManager.setLevel(targetName, "15");
                }

                dataManager.getStorages().get(targetName).setRank("premium");
                dataManager.save(targetName, dataManager.getStorages().get(targetName).getUUID());

                if(Bukkit.getPlayer(targetName) != null)
                {
                    Player target = Bukkit.getPlayer(targetName);
                    main.getScoreboardManager().updatePlayerRank(target);
                    CRank r = dataManager.getRank(target);
                    target.setPlayerListName("[" + r.getColor() + "" + r.getSigle() + "§r] " +  target.getName());
                }

            }
            else{
                sender.sendMessage("Ce joueur n'a jamais rejoins le serveur");
            }
        }

        if(args[0].equalsIgnoreCase("remove"))
        {
            if(main.getDataManager().getStorages().containsKey(targetName))
            {
                CYGDataManager dataManager = main.getDataManager();

                dataManager.getStorages().get(targetName).setRank("joueur");
                dataManager.save(targetName, dataManager.getStorages().get(targetName).getUUID());

                if(Bukkit.getPlayer(targetName) != null)
                {
                    Player target = Bukkit.getPlayer(targetName);
                    main.getScoreboardManager().updatePlayerRank(target);
                    CRank r = dataManager.getRank(target);
                    target.setPlayerListName("[" + r.getColor() + "" + r.getSigle() + "§r] " +  target.getName());
                }

            }
        }

        return false;
    }
}
