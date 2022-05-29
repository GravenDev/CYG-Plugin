package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.all.GameModeMenu;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerGameModeCommand extends TCommand {

    public PlayerGameModeCommand(CYG main) {
        super(main, false, false, false, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(!player.isOp() && player.getWorld().getName().equalsIgnoreCase("world"))
        {
            player.sendMessage("§cImpossible de /gamemode au hub !");
            return;
        }

        if((!getPlot(player.getName()).getFriends().contains(player.getName()))
                && !player.getWorld().getName().contains(player.getUniqueId().toString()))
        {
            player.sendMessage("§cIl est interdit de §l/gamemode§r§c dans un plot inconnu");
            return ;
        }

        menuManager().open(player, GameModeMenu.class);
    }

}
