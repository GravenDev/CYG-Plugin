package fr.gravencyg.commands.game;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameLeaveCommand extends TCommand {

    public GameLeaveCommand(CYG main) {
        super(main, false, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        player.teleport(getHubLocation());
        player.setGameMode(GameMode.SURVIVAL);
        gameManager().quit(player);
    }
}
