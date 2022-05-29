package fr.gravencyg.actions.potion;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.util.Collection;

public class PotionEffectAction extends Action {

    public PotionEffectAction(CYG main) {
        super(main, CLevel.LEVEL7);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "SendPotionEffect()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Appliquer un effet de potion";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        BrewingStand stand = (BrewingStand) block.getState();
        for(int i = 0; i < 3; i++)
        {
            ItemStack it = stand.getInventory().getItem(i);

            if(it == null) continue;

            if(it.getType() != Material.POTION && it.getType() != Material.SPLASH_POTION) continue;

            PotionMeta meta = (PotionMeta) it.getItemMeta();
            PotionType potionData = meta.getBasePotionData().getType();

            player.addPotionEffect(new PotionEffect(potionData.getEffectType(), 6000, potionData.getMaxLevel()));

            player.sendMessage("§rSendPotionEffect(§e"+ potionData.getEffectType().getName()+" " + potionData.getMaxLevel()+"§r)");
        }

    }
}
