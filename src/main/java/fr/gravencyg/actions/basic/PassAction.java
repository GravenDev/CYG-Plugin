package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PassAction extends Action {

    public PassAction(CYG main) {
        super(main, CLevel.LEVEL2);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "Pass()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Ce code ne fait rien";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess process) {}


}
