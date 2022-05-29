package fr.gravencyg.commands.mod;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModUnMuteCommand extends TCommand {

    public ModUnMuteCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        if(args.length == 0)
        {
            player.sendMessage("Utilise §e/§runmute §7[§epseudo§7");
            return;
        }

        String targetName = args[0];
        player.sendMessage("§rLe joueur §b"+targetName + "§r a été unmute !");
    }
}
