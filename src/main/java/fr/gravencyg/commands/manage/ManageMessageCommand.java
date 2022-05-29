package fr.gravencyg.commands.manage;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.messages.MessageMenu;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class ManageMessageCommand extends TCommand {

    private Random rd = new Random();

    public ManageMessageCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0) {
            player.sendMessage("§7/§emessages§r add [surnom] [votre_message] → " + ChatColor.GRAY + "Ajouter un nouveau message");
            player.sendMessage("§7/§emessages§r remove [surnom] → " + ChatColor.GRAY + "Retirer un message");
            player.sendMessage("§7/§emessages§r removeall → " + ChatColor.GRAY + "Retirer tout les messages");
            player.sendMessage("§7/§emessages§r menu → " + ChatColor.GRAY + "Ouvrir le menu des messages");
        }

        if(args.length >= 1) {
            if (args[0].equalsIgnoreCase("add")) {
                StringBuilder msgContent = new StringBuilder();

                if(args.length < 3)
                {
                    player.sendMessage("§7/§emessages§r add [surnom] [votre_message] → " + ChatColor.GRAY + "Ajouter un nouveau message");
                    return;
                }

                String msgName = args[1];

                for(String arg : args){
                    if(arg.equals(args[0]) ||  arg.equals(args[1])) continue;
                    msgContent.append(arg).append(" ");
                }

                dataManager().addMessage(player, msgName, msgContent.toString());
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 1) {
                    player.sendMessage("/messages remove [nom]");
                    return;
                }

                String messageName = args[1];
                dataManager().removeMessage(player, messageName);
            }

            if (args[0].equalsIgnoreCase("removeall")) {
                dataManager().removeAllMessages(player);
            }

            if (args[0].equalsIgnoreCase("menu")) {
                menuManager().open(player, MessageMenu.class);
            }

        }
    }

}
