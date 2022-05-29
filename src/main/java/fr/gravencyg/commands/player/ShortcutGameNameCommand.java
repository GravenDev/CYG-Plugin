package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.options.OptionMenu;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ShortcutGameNameCommand extends TCommand {

    public ShortcutGameNameCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0)
        {
            player.sendMessage("§7/§egamename§e [nom]" + ChatColor.GRAY + " Definit le nom du jeu");
            return;
        }

        StringBuilder gameName = new StringBuilder();

        for(String arg : args)
        {
            gameName.append(arg);
            gameName.append(" ");
        }

        dataManager().setGameName(player, gameName.toString());

        player.sendMessage("§rLe nom du jeu est désormais §7[§e"+gameName.toString()+"§7]");
    }
}
