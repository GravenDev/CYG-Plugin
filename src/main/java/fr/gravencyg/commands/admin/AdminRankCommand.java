package fr.gravencyg.commands.admin;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.all.ActionMenu;
import fr.gravencyg.utils.CRank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminRankCommand extends TCommand {

    public AdminRankCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(!player.isOp())
        {
            player.sendMessage("§CImpossible");
            return;
        }

        if (args.length < 2)
        {
            player.sendMessage("§7/§erank §7[§rpseudo§7] [§rjoueur§e,§r premium§e,§r modo§e,§r admin§7]");
            return;
        }

        String pseudo = args[0];
        String rank = args[1].toLowerCase();
        String rankS = rank.substring(0, 1).toUpperCase() + rank.substring(1);

        if(dataManager().getStorages().containsKey(pseudo))
        {
            dataManager().getStorages().get(pseudo).setRank(rankS);
            dataManager().save(pseudo, dataManager().getStorages().get(pseudo).getUUID());

            if(Bukkit.getPlayer(pseudo) != null)
            {
                Player target = Bukkit.getPlayer(pseudo);
                scoreboardManager().updatePlayerRank(target);
                CRank r = dataManager().getRank(target);
                target.setPlayerListName("[" + r.getColor() + "" + r.getSigle() + "§r] " +  player.getName());
            }

            player.sendMessage("Grade §b" + rankS + "§e donné à "+pseudo+"§r");
        }
        else
        {
            player.sendMessage("Joueur §e"+pseudo+"§r introuvable");
        }
    }
}
