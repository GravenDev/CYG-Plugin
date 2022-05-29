package fr.gravencyg.commands.manage;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class ManageFriendCommand extends TCommand {

    private Random rd = new Random();

    public ManageFriendCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, false, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0) {
            player.sendMessage("§7/§efriend§r add [surnom]" + ChatColor.GRAY + " Ajouter un ami sur votre parcelle");
            player.sendMessage("§7/§efriend§r remove [surnom] → " + ChatColor.GRAY + " Retirer un ami de votre parcelle");
            player.sendMessage("§7/§efriend§r plot [surnom] → " + ChatColor.GRAY + " Se teleporter sur la parcelle d'un ami");
            player.sendMessage("§7/§efriend§r list → " + ChatColor.GRAY + "Liste les amis sur votre parcelle");
        }

        if(args.length >= 1) {
            if (args[0].equalsIgnoreCase("add")) {

                if(args.length == 1)
                {
                    player.sendMessage("§7/§efriend§r add [surnom]" + ChatColor.GRAY + " Ajouter un ami sur votre parcelle");
                    return;
                }

                String pseudo = args[1];

                Plot plot = main.getPlotManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());

                if(!plot.getFriends().contains(pseudo)) {
                    player.sendMessage("§rNouvel ami §e" + pseudo + "§r ajouté à votre parcelle !");
                    plot.getFriends().add(pseudo);
                    dataManager().save(player);
                }
                else
                {
                    player.sendMessage("§r" + pseudo + "§r est déjà dans vos amis !");
                }

            }

            if (args[0].equalsIgnoreCase("plot")) {
                String pseudo = args[1];

                if(args.length < 2){
                    player.sendMessage("§7/§efriend§r plot [surnom] → " + ChatColor.GRAY + " Se teleporter sur la parcelle d'un ami");
                    return;
                }

                if(!hasPlot(pseudo)){
                    player.sendMessage("§rLe joueur §9" + pseudo + "§r n'a pas de plot !");
                    return;
                }

                Plot plot = main.getPlotManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());

                WorldCreator worldCreator = new WorldCreator(getPlotUUID(pseudo));
                worldCreator.type(WorldType.FLAT);
                worldCreator.generateStructures(false);

                World playerWorld = Bukkit.createWorld(worldCreator);

                if(plot.getFriends().contains(player.getName())) {
                    player.sendMessage("§rTéléportation vers la parcelle de §b" + pseudo + "§r !");
                    player.teleport(new Location(playerWorld, 0, 10, 0));
                }
                else
                {
                    player.sendMessage("§rVous n'êtes pas ami avec §c" + pseudo);
                }
            }

            if (args[0].equalsIgnoreCase("remove")) {
                String pseudo = args[1];

                Plot plot = getPlot(player);

                if(plot.getFriends().contains(pseudo)) {
                    player.sendMessage("§r" + pseudo + "§c a été retiré de votre parcelle !");
                    plot.getFriends().remove(pseudo);
                    dataManager().save(player);
                }
                else
                {
                    player.sendMessage("§r" + pseudo + "§c n'est pas dans vos amis !");
                }
            }

            if(args[0].equalsIgnoreCase("list"))
            {
                player.sendMessage("§eListe de vos amis§r pour ce Plot: ");
                Plot plot = main.getPlotManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());

                for(String string : plot.getFriends())
                {
                    player.sendMessage("§e-§r " + string);
                }
            }

        }
    }

}
