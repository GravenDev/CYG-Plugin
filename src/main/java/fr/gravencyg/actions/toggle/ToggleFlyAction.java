package fr.gravencyg.actions.toggle;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class ToggleFlyAction extends Action {

    private Random random = new Random();

    public ToggleFlyAction(CYG main) {
        super(main, CLevel.LEVEL13);
    }

    @Override
    public String title() {
        return ChatColor.BLUE +"ToggleFly()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY +"Activer/Désactiver le vol d'oiseau";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        player.setAllowFlight(true);
        if(!player.isFlying())
        {
            player.sendMessage("§bVol d'oiseau Actif !");
            player.setFlying(true);
            player.setAllowFlight(true);
        }
        else{
            player.sendMessage("§bVol d'oiseau Inactif !");
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }

}
