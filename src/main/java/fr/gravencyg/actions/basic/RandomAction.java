package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class RandomAction extends Action {

    private Random random = new Random();

    public RandomAction(CYG main) {
        super(main, CLevel.LEVEL1);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "Random()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Ajouter du hasard à une instruction";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

    }

}
