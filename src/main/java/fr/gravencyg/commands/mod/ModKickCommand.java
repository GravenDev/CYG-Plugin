package fr.gravencyg.commands.mod;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModKickCommand extends TCommand {

    public ModKickCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        if(args.length < 2)
        {
            player.sendMessage("Utilise §e/§rkick §7[§epseudo§7] §7[§eRaison§7]§7");
            return;
        }
        String targetName = args[0];

        if(Bukkit.getPlayer(targetName) != null)
        {
            Player target = Bukkit.getPlayer(targetName);
            StringBuilder reason = new StringBuilder();

            for(String arg : args){
                if(!args[0].equalsIgnoreCase(arg))
                {
                    reason.append(arg + " ");
                }
            }

            player.sendMessage(targetName + " a été éjécté pour la raison §c>>§r " + reason.toString());
            target.kickPlayer("§r[§cKick par "+player.getName()+"§r] \n\n§pPour la raison: §c\n"+reason.toString());

        }
    }
}
