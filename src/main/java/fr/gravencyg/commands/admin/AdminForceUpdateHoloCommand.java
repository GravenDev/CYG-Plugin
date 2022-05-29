package fr.gravencyg.commands.admin;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AdminForceUpdateHoloCommand extends TCommand {

    public AdminForceUpdateHoloCommand(CYG main) {
        super(main, true, true, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(!player.isOp())
        {
            player.sendMessage("§CImpossible");
            return;
        }

       for(Player pl : Bukkit.getOnlinePlayers())
       {
           main.getHologramManager().registerHologram(pl.getWorld());
       }


    }
}
