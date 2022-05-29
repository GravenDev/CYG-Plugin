package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CRank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerFlyCommand extends TCommand {

    public PlayerFlyCommand(CYG main) {
        super(main, false, false, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if(main.getDataManager().getRank(player) != CRank.PREMIUM && main.getDataManager().getRank(player).isNotMod())
        {
            player.sendMessage("§fUniquement pour les §ePremiums§r et le §9Staff");

            return;
        }

        if(!player.isFlying()) {
            player.sendMessage("§bVol d'oiseau §b>>§r Actif");
            player.setAllowFlight(true);
            player.setFlying(true);
        }
        else{
            player.sendMessage("§9Vol d'oiseau §b>>§r Inactif");
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }
}
