package fr.gravencyg.actions.message;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Plot;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MessageAction extends Action {

    public MessageAction(CYG main) {
        super(main, CLevel.LEVEL2);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "SendMessage()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Envoyer un message au joueur";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        Plot plot = main.getDataManager().getPlotFromWorldLocationOf(player);

        if(plot.getMessageSet().containsKey(LocationUtils.fromLocToString(block.getLocation()))) {
            String messageLocName = plot.getMessageSet().get(LocationUtils.fromLocToString(block.getLocation()));
            String message = plot.getMessages().get(messageLocName)
                    .replace("&", "§");

            player.sendMessage(message);
        }

    }
}
