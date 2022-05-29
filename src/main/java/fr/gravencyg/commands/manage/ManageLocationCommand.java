package fr.gravencyg.commands.manage;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.locations.LocationMenu;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class ManageLocationCommand extends TCommand {

    public ManageLocationCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0) {
            player.sendMessage("§7/§elocations§r add [surnom] → " + ChatColor.GRAY + "Ajouter une nouvelle zone");
            player.sendMessage("§7/§elocations§r remove [surnom] → " + ChatColor.GRAY + "Retirer une zone");
            player.sendMessage("§7/§elocations§r removeall → " + ChatColor.GRAY + "Retirer toutes les zones");
            player.sendMessage("§7/§elocations§r menu → " + ChatColor.GRAY + "Ouvrir le menu des zones");
        }

        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("add"))
            {
                Random rd = new Random();
                String locationName = "zone-" + rd.nextInt(9999);

                if(args.length == 2)
                {
                    locationName = args[1];
                }
                dataManager().addLocation(player, locationName, player.getLocation());
            }
            if(args[0].equalsIgnoreCase("remove"))
            {
                if(args.length == 1)
                {
                    player.sendMessage("/locations remove [nom]");
                    return;
                }

                String locationName = args[1];
                dataManager().removeLocation(player, locationName);
            }

            if(args[0].equalsIgnoreCase("removeall"))
            {
                dataManager().removeAllLocations(player);
            }

            if(args[0].equalsIgnoreCase("menu"))
            {
                menuManager().open(player, LocationMenu.class);
            }
        }
    }

}
