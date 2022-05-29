package fr.gravencyg.actions.game;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class EliminateAction extends Action {

    private Random random = new Random();

    public EliminateAction(CYG main) {
        super(main, CLevel.LEVEL1);
    }

    @Override
    public String title() {
        return ChatColor.BLUE +"Eliminate()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY +"Elimine le joueur de la partie";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        if(main.getGameManager().isPlayerInGame(player).size() != 0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            main.getGameManager().eliminate(player, game);
        }

    }

}
