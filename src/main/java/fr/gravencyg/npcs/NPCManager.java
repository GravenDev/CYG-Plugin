package fr.gravencyg.npcs;

import fr.gravencyg.CYG;
import fr.gravencyg.npcs.all.DevNPC;
import fr.gravencyg.npcs.all.PlayNPC;
import fr.gravencyg.npcs.all.UpgradeNPC;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {

    private List<EntityPlayer> npcs = new ArrayList<>();
    private List<NPC> npcsObj = new ArrayList<>();
    private CYG main;

    public NPCManager(CYG main) {
        this.main = main;

        registerNPC(new DevNPC(main));
        registerNPC(new PlayNPC(main));
        registerNPC(new UpgradeNPC(main));

        for(int i = 0; i < npcs.size(); i++) {
            EntityPlayer npc = npcs.get(i);
            NPC npcObj = npcsObj.get(i);
            Location location = npc.getBukkitEntity().getLocation();

            ArmorStand top = main.getHologramManager().registerHologram(location, "§e>> "+ npcObj.hologramMessage() +" <<");

            Location adjust = new Location(location.getWorld(), location.getX() + npcObj.colliderOffset()[0], location.getY(), location.getZ() + npcObj.colliderOffset()[1]);
            ArmorStand stand = main.getHologramManager().registerBoxCollider(adjust, "§e>> "+ npcObj.hologramMessage() +" <<");
            npcObj.setTopArmorstand(stand);

        }
    }

    public void registerNPC(NPC npc) {
        npcs.add(npc.create());
        npcsObj.add(npc);
    }

    public void display(Player player) {
        for (EntityPlayer npc : npcs) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc)); // Spawns the NPC for the player client.
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360))); // Correct head rotation when spawned in player look direction.
        }
    }

    public List<NPC> getNPCSObj() { return npcsObj; }

    public void killAll() {
        for(World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.ARMOR_STAND) {
                    entity.remove();
                }
            }
        }
    }
}
