package fr.gravencyg.commands.mod;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.model.Storage;
import fr.gravencyg.model.VerifyGameConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModVerifyGameCommand extends TCommand {

    public ModVerifyGameCommand(CYG main) {
        super(main, true, false, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if (args.length < 2)
        {
            player.sendMessage("§7/§everifygame §7[§rpseudo§7] [§r§9DIAMOND_SWORD§7]§r §7[§rNUMERO_CASE§7]");
            player.sendMessage("§7/§everifygame §7[§rpseudo§7] cancel");
            return;
        }

        String pseudo = args[0];

        if(args[1].equalsIgnoreCase("cancel"))
        {

            // unverify player
            Storage targetStorage = dataManager().getStorages().get(pseudo);

            player.sendMessage("§aLe jeu "+targetStorage.getPlot(player.getWorld().getName()).getName()+"§r est désormais §CREFUSE");

            targetStorage.getPlot(player.getWorld().getName()).getVerifyConfig().setVerify(false);
            dataManager().save(pseudo, targetStorage.getUUID());
            // TODO gameManager().getVerifyGames().remove(pseudo);
        }
        else
        {
            // verify player
            String material = args[1];
            String numberSlot = args[2];

            Storage targetStorage = dataManager().getStorages().get(pseudo);

            player.sendMessage("§aLe jeu "+targetStorage.getPlot(player.getWorld().getName()).getName()+"§r est désormais §aVALIDE");

            VerifyGameConfig verifyGameConfig = targetStorage.getPlot(player.getWorld().getName()).getVerifyConfig();
            verifyGameConfig.setVerify(true);
            verifyGameConfig.setIcon(material);
            verifyGameConfig.setSlot(Integer.valueOf(numberSlot));

            if(!gameManager().getVerifyGames().containsKey(pseudo))
            {
                gameManager().createStorageFullData(targetStorage);
            }

            dataManager().save(pseudo, targetStorage.getUUID());
        }

    }
}
