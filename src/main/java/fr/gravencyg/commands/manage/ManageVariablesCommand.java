package fr.gravencyg.commands.manage;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.messages.MessageMenu;
import fr.gravencyg.menus.variables.VariableMenu;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Random;

public class ManageVariablesCommand extends TCommand {

    private Random rd = new Random();

    public ManageVariablesCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(args.length == 0) {
            player.sendMessage("§7/§evariables§r add [nom] [type] [contenu de la variable] → " + ChatColor.GRAY + "Ajouter une nouvelle variable");
            player.sendMessage("§7/§evariables§r menu → " + ChatColor.GRAY + "Ouvrir le menu des variables");
        }

        if(args.length >= 1) {
            if (args[0].equalsIgnoreCase("add")) {

                if(args.length < 4)
                {
                    player.sendMessage("§7/§evariables§r add [nom] [type] [contenu de la variable] → " + ChatColor.GRAY + "Ajouter une nouvelle variable");
                    return;
                }

                StringBuilder varContent = new StringBuilder();
                String varName = args[1];
                String type = args[2];

                if(!type.equalsIgnoreCase("int") && !type.equalsIgnoreCase("string") && !type.equalsIgnoreCase("double"))
                {
                    player.sendMessage("Types de variables autorisés : ");
                    player.sendMessage("§bint§r pour les nombres");
                    player.sendMessage("§bstring§r pour les textes");
                    player.sendMessage("§bdouble§r pour les nombres à virgule");
                    return;
                }

                for(String arg : args){
                    if(arg.equals(args[0]) ||  arg.equals(args[1]) || arg.equals(args[2])) continue;
                    varContent.append(arg).append(" ");
                }

            }
            if (args[0].equalsIgnoreCase("remove")) {

            }

            if (args[0].equalsIgnoreCase("removeall")) {

            }

            if (args[0].equalsIgnoreCase("menu")) {
                menuManager().open(player, VariableMenu.class);
            }

        }
    }

}
