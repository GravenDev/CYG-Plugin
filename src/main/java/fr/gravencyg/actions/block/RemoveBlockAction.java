package fr.gravencyg.actions.block;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.MaterialAndData;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RemoveBlockAction extends Action {

    private Random random = new Random();

    public RemoveBlockAction(CYG main) {
        super(main, CLevel.LEVEL6);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "RemoveBlock()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Supprime un bloc";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        if(main.getGameManager().isPlayerInGame(player).size() != 0) {
            if(actionProcess.getBlock().getType() != Material.AIR)
            {
                CYGame game = main.getGameManager().getCurrentGame(player);
                game.getBreakBlocks().put(actionProcess.getBlock().getLocation(), new MaterialAndData(actionProcess.getBlock().getType(), actionProcess.getBlock().getData()));
                actionProcess.getBlock().setType(Material.AIR);
            }
        }
        else{
            player.sendMessage("§eRemoveBlock(event.getBlock())");
        }

    }

}
