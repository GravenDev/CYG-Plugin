package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.menus.plot.PlotManageMenu;
import fr.gravencyg.menus.plot.PlotMenu;
import fr.gravencyg.utils.CLevel;
import org.bukkit.entity.Player;

public class PlayerPlotCommand extends TCommand {

    public PlayerPlotCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        if (player.getWorld().getName().contains(player.getUniqueId().toString())) {
            main.getMenuManager().open(player, PlotManageMenu.class);
        } else {
            main.getMenuManager().open(player, PlotMenu.class);
        }
    }
}
