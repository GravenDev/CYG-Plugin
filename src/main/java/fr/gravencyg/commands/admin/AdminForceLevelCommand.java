package fr.gravencyg.commands.admin;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.entity.Player;

public class AdminForceLevelCommand extends TCommand {

    public AdminForceLevelCommand(CYG main) {
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
            player.sendMessage("§7/§eforcelevel §7[§rpseudo§7] [lvl]");
            return;
        }

        String pseudo = args[0];
        String newLevel = args[1];

        main.getDataManager().setLevel(pseudo, newLevel);
        main.getDataManager().refreshLevel(pseudo);
        player.sendMessage("Le joueur §e"+pseudo+"§r est désormais niveau §e" + newLevel);
        main.getScoreboardManager().updatePlayerLevel(player);
        main.getScoreboardManager().updatePlayerTrophy(player);


    }
}
