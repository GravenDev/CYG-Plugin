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

public class WinnerAction extends Action {

    private Random random = new Random();

    public WinnerAction(CYG main) {
        super(main, CLevel.LEVEL5);
    }

    @Override
    public String title() {
        return ChatColor.BLUE +"Win()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY +"Le joueur courant gagne la partie";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        if(main.getGameManager().isPlayerInGame(player).size() != 0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            for(Player p : game.getPlayers())
            {
                if(p != player)  main.getGameManager().eliminate(p, game);
            }
        }

    }

}
