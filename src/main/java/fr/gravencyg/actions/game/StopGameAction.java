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

public class StopGameAction extends Action {

    private Random random = new Random();

    public StopGameAction(CYG main) {
        super(main, CLevel.LEVEL5);
    }

    @Override
    public String title() {
        return ChatColor.BLUE +"StopGame()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY +"Arrete le jeu actuel";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        if(main.getGameManager().isPlayerInGame(player).size() != 0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            main.getGameManager().stop(game);
        }

    }

}
