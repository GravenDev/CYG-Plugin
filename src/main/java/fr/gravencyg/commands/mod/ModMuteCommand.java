package fr.gravencyg.commands.mod;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModMuteCommand extends TCommand {

    public ModMuteCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0)
        {
            player.sendMessage("Utilise §e/§rmute §7[§epseudo§7] [nombreSecondes]");
            return;
        }

        String targetName = args[0];
        String duration = "100000";

        if(args.length == 2) {
            duration = args[1];
        }

        if(Integer.valueOf(duration) != null)
        {
            player.sendMessage(targetName + " a été mute pendant " + duration + "§bs");
        }
    }
}
