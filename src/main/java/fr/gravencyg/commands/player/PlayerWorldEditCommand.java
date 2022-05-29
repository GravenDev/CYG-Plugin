package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerWorldEditCommand extends TCommand {

    public PlayerWorldEditCommand(CYG main) {
        super(main, CLevel.LEVEL15,false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0)
        {
            player.sendMessage("§7/§ewe§r give" + ChatColor.GRAY + " Accès à l'outil du mini worldedit");
            player.sendMessage("§7/§ewe§r set [type] [data]" + ChatColor.GRAY + " Remplacer la selection par un bloc");
            player.sendMessage("§7/§ewe§r undo" + ChatColor.GRAY + " Revenir en arrière");
            player.sendMessage("§7/§ewe§r unset" + ChatColor.GRAY + " Supprimer la selection");
            return;
        }

        if(args[0].equalsIgnoreCase("give"))
        {
            worldEditManager().give(player);
        }

        if(args[0].equalsIgnoreCase("set"))
        {

            if(args.length == 1)
            {
                player.sendMessage("§7/§ewe§r set [type] [id]" + ChatColor.GRAY + " Remplacer la selection par un bloc");
                return;
            }

            int id = 0;

            if(args.length == 3) id = Integer.parseInt(args[2]);

            worldEditManager().set(player, args[1].toLowerCase(), id);

        }

        if(args[0].equalsIgnoreCase("undo"))
        {
            worldEditManager().undo(player);
        }

        if(args[0].equalsIgnoreCase("unset"))
        {
            worldEditManager().unset(player);
        }

    }
}
