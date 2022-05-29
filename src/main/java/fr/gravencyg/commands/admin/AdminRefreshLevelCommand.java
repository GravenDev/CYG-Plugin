package fr.gravencyg.commands.admin;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AdminRefreshLevelCommand extends TCommand {

    public AdminRefreshLevelCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(!player.isOp())
        {
            player.sendMessage("§CImpossible");
            return;
        }

        if (args.length == 0)
        {
            player.sendMessage("§7/§erefreshlevel §7[§rpseudo§7]");
            return;
        }

        String pseudo = args[0];
        main.getDataManager().refreshLevel(pseudo);
        player.sendMessage("Niveau mis à jour pour le joueur §9" + pseudo);
        main.getScoreboardManager().updatePlayerLevel(player);

    }
}
