package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerBlockConditionCommand extends TCommand {

    public PlayerBlockConditionCommand(CYG main) {
        super(main, CLevel.LEVEL1,false, false, false, false);
    }

    public void onExecute(Player player, String[] args) {
        ItemStack eventBlock = new ItemBuilder(Material.HOPPER).setName(ChatColor.BLUE+"If()")
                .addLoreLine("§7Verifier si quelquechose est vrai").toItemStack();
        player.getInventory().addItem(eventBlock);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 3);
    }

}
