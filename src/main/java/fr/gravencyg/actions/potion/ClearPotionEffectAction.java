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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class ClearPotionEffectAction extends Action {

    public ClearPotionEffectAction(CYG main) {
        super(main, CLevel.LEVEL7);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "ClearPotionEffects()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Supprimer tout les effets de potions";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        for(PotionEffect potionEffect : player.getActivePotionEffects())
        {
            player.removePotionEffect(potionEffect.getType());
        }
        player.sendMessage("§9ClearPotionEffects()");
    }
}
