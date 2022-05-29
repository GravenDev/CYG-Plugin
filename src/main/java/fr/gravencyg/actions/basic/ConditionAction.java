package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.actions.team.Team;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class ConditionAction extends Action {

    private Random random = new Random();

    public ConditionAction(CYG main) {
        super(main, CLevel.LEVEL2);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "If()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Verifier si quelquechose est vrai";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        Location bottomBlock = block.getLocation().clone().add(0, -1, 0);
        Location topBlock = block.getLocation().clone().add(0, 1, 0);


        if(bottomBlock.getBlock().getType() == null) return;

        /*if(topBlock.getBlock().getType() == Material.WOOL )
        {
            if(main.getGameManager().isPlayerInGame(player).size() != 0)
            {
                CYGame game = main.getGameManager().getCurrentGame(player);
                Team team = Team.getByWoolData(topBlock.getBlock().getData());
                if(team != null && game.getTeams().containsKey(team))
                {
                    if(!game.getTeams().get(team).contains(player)){
                        player.sendMessage("Vous n'êtes pas dans l'equipe " + team.getColor() + team.getName());
                        actionProcess.getProcessedBlocks().clear();
                    }
                }
            }
        }*/

        if (bottomBlock.getBlock().getType() != actionProcess.getMaterial() && bottomBlock.getBlock().getType() != Material.GRASS){
            actionProcess.getProcessedBlocks().clear();
        }

    }


}
