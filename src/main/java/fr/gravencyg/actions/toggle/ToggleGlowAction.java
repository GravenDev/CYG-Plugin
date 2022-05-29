package fr.gravencyg.actions.toggle;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ToggleGlowAction extends Action {

    public ToggleGlowAction(CYG main) {
        super(main, CLevel.LEVEL16);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "Glow()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Activer/Desactiver l'effet de glow sur un joueur";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        player.sendMessage("§6glow()");

        if(player.isGlowing())
        {
            player.setGlowing(false);
        }
        else{
            player.setGlowing(true);
        }

    }
}
