package fr.gravencyg.listeners;

import fr.gravencyg.CYG;
import fr.gravencyg.menus.all.EventMenu;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.potion.PotionEffect;

public class WorldListener implements Listener {

    private CYG main;

    public WorldListener(CYG main) {
        this.main = main;
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFire(BlockBurnEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onFeed(FoodLevelChangeEvent event){
        if(event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if(player.getWorld().getName().equalsIgnoreCase("world")) {
            main.getNpcManager().display(player);
            player.setLevel(main.getDataManager().getLevel(player.getName()));
            player.setHealth(20);
            player.setGameMode(GameMode.SURVIVAL);

            if(main.getFreezeGamePlayers().contains(player)){
                main.getFreezeGamePlayers().remove(player);
            }

            for(PotionEffect potionEffect : player.getActivePotionEffects())
            {
                player.removePotionEffect(potionEffect.getType());
            }

            if(player.getName().equalsIgnoreCase("GravenYT")) {
                player.setGameMode(GameMode.CREATIVE);
            }

            if(!player.isOp()) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }

        }
        else{

            main.getHologramManager().registerHologram(player.getWorld());
        }

        player.getWorld().setGameRuleValue("announceAdvancements", "false");
        player.getInventory().clear();
        player.getWorld().setTime(0);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockDisable(BlockRedstoneEvent e) {
        e.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void onPortal(PortalCreateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM &&
                (event.getEntity().getType() != EntityType.PLAYER && event.getEntity().getType() != EntityType.ARMOR_STAND)
        && (event.getEntity().getType() != EntityType.BOAT && event.getEntity().getType() != EntityType.WITHER)
        && (event.getEntity().getType() != EntityType.ENDER_DRAGON))
        {
            event.setCancelled(true);
        }
    }

}