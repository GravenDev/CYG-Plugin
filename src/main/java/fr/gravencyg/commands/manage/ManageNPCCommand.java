package fr.gravencyg.commands.manage;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.locations.LocationMenu;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class ManageNPCCommand extends TCommand {

    public ManageNPCCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0) {
            player.sendMessage("§7/§enpc§r add [surnom] [pseudo du joueur mc] → " + ChatColor.GRAY + " Créer un NPC avec comme pseudo le nom et comme tête celle du pseudo");
        }

        if(args.length >= 1) {

        }
    }

}
