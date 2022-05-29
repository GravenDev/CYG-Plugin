package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChangeGameModeAction extends Action {

    public ChangeGameModeAction(CYG main) {
        super(main, CLevel.LEVEL3);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "ChangeGameMode()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Change le mode de jeu";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        int level = 0;

        for(int i = 0; i < 4; i++)
        {
            Block top = block.getLocation().clone().add(0, i+1, 0).getBlock();
            if(top.getType() != Material.OBSERVER) break;
            level++;
        }

        if(level == 0)
        {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("§rPassage en §asurvie !");
        }

        if(level == 1)
        {
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage("§rPassage en §caventure !");
        }

        if(level == 2)
        {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§rPassage en §9créatif !");
        }

        if(level == 3)
        {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§rPassage en §7spectateur !");
        }

    }
}
