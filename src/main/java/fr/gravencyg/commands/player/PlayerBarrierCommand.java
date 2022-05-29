package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerBarrierCommand extends TCommand {

    public PlayerBarrierCommand(CYG main) {
        super(main, CLevel.LEVEL3, false, false, true, false);
    }

    @Override
    public void onExecute(Player player,  String[] args) {
        player.sendMessage("§9+1§l barriere");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2, 4);
        player.getInventory().addItem(new ItemStack(Material.BARRIER));
    }

}
