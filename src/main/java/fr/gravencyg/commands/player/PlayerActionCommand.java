package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.all.ActionMenu;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.CRank;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerActionCommand extends TCommand {

    public PlayerActionCommand(CYG main) {
        super(main, CLevel.LEVEL1,false, true, false, false);
    }

    public void onExecute(Player player, String[] args) {

        main.getMenuManager().open(player, ActionMenu.class);
    }

}
