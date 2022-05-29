package fr.gravencyg.manager;

import fr.gravencyg.CYG;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class CYGHologramManager {

    private CYG main;

    public CYGHologramManager(CYG main) { this.main = main; }

    private Map<Location, ArmorStand> holograms = new HashMap<>();

    public void registerHologram(World world) {

        Storage targetStorage = main.getDataManager().getStorageByUUID(world.getName());

        if(targetStorage != null && targetStorage.getPlot(world.getName()) != null)
        {
            for (Map.Entry<String, String> e: targetStorage.getPlot(world.getName()).getEvents().entrySet()) {
                Location loc = LocationUtils.fromStringToLoc(world.getName(), e.getValue());
                if(!holograms.containsKey(loc)) {
                    loc.getBlock().setType(Material.LAPIS_BLOCK);
                    registerEventHologram(loc, e.getKey());
                }

            }
        }
    }

    public ArmorStand registerHologram(Location loc, String text) {

        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY() + 0.3, loc.getZ(), loc.getYaw(), loc.getPitch());
        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        stand.setCustomName(text);
        stand.setCustomNameVisible(true);
        stand.setSmall(false);
        stand.setGravity(false);
        stand.setVisible(false);

        // register hologram
        holograms.put(loc, stand);
        return stand;
    }

    public ArmorStand registerBoxCollider(Location loc, String text) {

        Location location = new Location(loc.getWorld(), loc.getX(), loc.getY() + 0.1, loc.getZ(), loc.getYaw(), loc.getPitch());
        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        stand.setCustomName(text);
        stand.setCustomNameVisible(false);
        stand.setSmall(false);
        stand.setGravity(false);
        stand.setVisible(false);

        // register hologram
        holograms.put(loc, stand);
        return stand;
    }

    public ArmorStand registerEventHologram(Location loc, String eventName) {

        Location location = new Location(loc.getWorld(), loc.getX() + 0.5, loc.getY() + 1, loc.getZ() + 0.5, loc.getYaw(), loc.getPitch());
        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        stand.setCustomName(ChatColor.BLUE + eventName + "()");
        stand.setCustomNameVisible(true);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setVisible(false);

        // register hologram
        holograms.put(loc, stand);
        return stand;
    }

    public void removeHologram(Block block) {
        if (holograms.containsKey(block.getLocation())) {
            ArmorStand stand = holograms.get(block.getLocation());
            stand.remove();
        }
    }

    public void removeAllHolograms() {
        for(Map.Entry<Location, ArmorStand> stands : holograms.entrySet()) {
            removeHologram(stands.getKey().getBlock());
        }
    }


}
