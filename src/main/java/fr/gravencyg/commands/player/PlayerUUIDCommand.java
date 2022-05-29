package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerUUIDCommand extends TCommand {

    public PlayerUUIDCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        player.sendMessage("§eVotre UUID §l>>§r " + player.getUniqueId().toString());
    }
}
