package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class KillMonsterAction extends Action {

    public KillMonsterAction(CYG main) {
        super(main, CLevel.LEVEL11);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "KillMonster()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Tuer toutes les entités du monde";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {

        for(Entity entity : block.getWorld().getEntities())
        {
            if(entity.getType() == EntityType.ARMOR_STAND || entity.getType() == EntityType.PLAYER) continue;
            entity.remove();
        }

    }


}
