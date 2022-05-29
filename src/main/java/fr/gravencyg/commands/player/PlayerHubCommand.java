package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerHubCommand extends TCommand {

    public PlayerHubCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        player.teleport(getHubLocation());
        player.setGameMode(GameMode.SURVIVAL);

        gameManager().quit(player);
        player.sendTitle("§eGravenMC", "§6Code tes propres mini-jeux");
    }
}
