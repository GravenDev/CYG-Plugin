package fr.gravencyg.npcs;

import fr.gravencyg.CYG;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class NPCClickListener implements Listener {

    private CYG main;

    public NPCClickListener(CYG main) { this.main = main;}

    @EventHandler(priority= EventPriority.HIGH)
    public void onPlayerClick(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        Entity entity = event.getRightClicked();

        if(entity.getType() == EntityType.ARMOR_STAND && loc.getWorld().getName().equalsIgnoreCase("world"))
        {
            for(int i = 0; i < main.getNpcManager().getNPCSObj().size(); i++)
            {
                NPC npcObj = main.getNpcManager().getNPCSObj().get(i);
                ArmorStand stand = npcObj.getStand();

                if(stand.getCustomName().equalsIgnoreCase(entity.getCustomName()))
                {
                    npcObj.onClick(player);
                }
            }
        }
    }

}
