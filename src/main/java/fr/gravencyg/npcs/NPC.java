package fr.gravencyg.npcs;

import com.mojang.authlib.GameProfile;
import fr.gravencyg.CYG;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.MinecraftServer;
import net.minecraft.server.v1_16_R1.PlayerInteractManager;
import net.minecraft.server.v1_16_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class NPC {

    private String displayName;

    private ArmorStand stand;

    protected CYG main;

    private Location location;

    public NPC(CYG main, String displayName, double x, double y, double z, float yaw, float pitch) {
        this.main = main;
        this.displayName = displayName;
        this.location = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);
    }

    public abstract String skinUUID();

    public abstract String hologramMessage();

    public abstract double[] colliderOffset();

    public abstract void onClick(Player player);

    public Location getLocation() { return location;  }

    public EntityPlayer create() {
        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorld("world")).getHandle(); // Change "world" to the world the NPC should be spawned in.
        GameProfile gameProfile = new GameProfile(UUID.fromString(skinUUID()), "[NPC] "+displayName); // Change "playername" to the name the NPC should have, max 16 characters.
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        return npc;
    }

    public void setTopArmorstand(ArmorStand stand) {
        this.stand = stand;
    }

    public ArmorStand getStand() { return stand; }
}
