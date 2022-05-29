package fr.gravencyg.actions.block;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.MaterialAndData;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class SetBlockAction extends Action {

    private Random random = new Random();

    public SetBlockAction(CYG main) {
        super(main, CLevel.LEVEL6);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "SetBlock()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Place un bloc à un endroit";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        Block top = block.getLocation().clone().add(0, 1, 0).getBlock();

        if(top.getType() == Material.AIR) return;

        if(main.getGameManager().isPlayerInGame(player).size() != 0) {
            if(actionProcess.getBlock().getType() != Material.AIR)
            {
                CYGame game = main.getGameManager().getCurrentGame(player);

                if(!game.getBreakBlocks().containsKey(actionProcess.getBlock().getLocation())) {
                    game.getBreakBlocks().put(
                            actionProcess.getBlock().getLocation(),
                            new MaterialAndData(actionProcess.getBlock().getType(), actionProcess.getBlock().getData())
                    );
                }

                // place new block
                actionProcess.getBlock().setType(top.getType());
               // actionProcess.getBlock().setData(top.getData());

            }
        }
        else{
            player.sendMessage("§eSetBlock(event.getBlock())");
        }

    }

}
