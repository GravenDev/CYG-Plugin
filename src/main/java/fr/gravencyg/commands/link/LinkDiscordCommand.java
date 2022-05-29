package fr.gravencyg.commands.link;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.entity.Player;

public class LinkDiscordCommand extends TCommand {

    public LinkDiscordCommand(CYG main) {
        super(main, CLevel.LEVEL1, false, false, false, true);
    }

    @Override
    public void onExecute(Player player, String[] args) {
        player.sendMessage("§bhttps://discord.gg/gravenmc");
    }

}
