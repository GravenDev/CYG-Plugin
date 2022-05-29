package fr.gravencyg.commands.mod;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModFreezeCommand extends TCommand {

    private CYG main;

    public ModFreezeCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        if(args.length == 0)
        {
            player.sendMessage("Utilise §e/§rfreeze §7[§epseudo§7]");
            return;
        }

        String targetName = args[0];

        if(Bukkit.getPlayer(targetName) != null)
        {

            Player target = Bukkit.getPlayer(targetName);

            if(!main.getFreezeModPlayers().contains(target))
            {
                player.sendMessage("§b" + targetName + "§r a été gelé");
                main.getFreezeModPlayers().add(target);
            }
            else
            {
                player.sendMessage("§b" + targetName + "§r a été dégelé");
                main.getFreezeModPlayers().remove(target);
            }
        }
    }
}
