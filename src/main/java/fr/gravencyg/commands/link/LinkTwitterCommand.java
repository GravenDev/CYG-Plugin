package fr.gravencyg.commands.link;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.entity.Player;

public class LinkTwitterCommand extends TCommand {

    public LinkTwitterCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        player.sendMessage("§bhttps://twitter.com/gravenmc_cyg");
    }

}
