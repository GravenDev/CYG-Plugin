package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerPingCommand extends TCommand {

    public PlayerPingCommand(CYG main) {
        super(main, CLevel.LEVEL1,false, true, false, true);
    }

    public void onExecute(Player player, String[] args) {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        player.sendMessage("§9" + nmsPlayer.ping +"§r ms");
    }

}
