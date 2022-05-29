package fr.gravencyg.listeners;

import fr.gravencyg.CYG;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.game.CYGameState;
import fr.gravencyg.utils.CEvent;
import fr.gravencyg.utils.CRank;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerListener implements Listener {

    private CYG main;

    public PlayerListener(CYG main) {
        this.main = main;
    }

    @EventHandler
    public void onPing(ServerListPingEvent e){
        e.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // Set player to CREATIVE mode
        player.setGameMode(GameMode.SURVIVAL);

        // Teleport to hub
        player.teleport(main.spawnLocation);

        if(!player.isOp()) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }

        // Set Hub time to 0
        player.getWorld().setTime(0);
        player.getInventory().clear();

        // Create player file
        main.getDataManager().createProfileIfNotExist(player);

        main.getDataManager().refreshLevel(player.getName());

        // Display NPCs
        main.getNpcManager().display(player);

        // Set Level
        player.setLevel(main.getDataManager().getLevel(player.getName()));

        // Join title
        player.sendTitle("§eGravenMC", "§6Code tes propres mini-jeux");

        // Set join message
        CRank rank = main.getDataManager().getRank(player);

        if(rank == CRank.PREMIUM || rank.isMod()) {
            event.setJoinMessage(rank.getColor() + "" + rank.getName() + ChatColor.RESET + " - " + player.getName() + ChatColor.RESET + " se connecte !");
        }
        else{
            event.setJoinMessage(null);
        }

        player.setPlayerListName(ChatColor.YELLOW + "§r[§r" + rank.getColor() + "" + rank.getSigle() + "§r] " +  player.getName());

        // Scoreboard
        main.getScoreboardManager().load(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        CRank rank = main.getDataManager().getRank(player);

        if(rank == CRank.PREMIUM || rank.isMod()) {
            event.setQuitMessage(rank.getColor() + "" + rank.getName() + ChatColor.RESET + " - " + player.getName() + ChatColor.RESET + " se deconnecte !");
        }
        else{
            event.setQuitMessage(null);
        }

        // Scoreboard
        main.getScoreboardManager().unload(player);
        main.getScoreboardManager().updateTotalPlayers();
        main.getGameManager().quit(player);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(main.getFreezeModPlayers().contains(player) || main.getFreezeGamePlayers().contains(player)) {
            event.setTo(event.getFrom());
        }

        if(main.getGameManager().isPlayerInGame(player).size()!=0)
        {
            CYGame game = main.getGameManager().getCurrentGame(player);
            if(game.isState(CYGameState.PLAYING))
            {
                if(player.getLocation().getY() <= 0) {
                    player.teleport(new Location(player.getWorld(), 0, 20, 0));
                    player.setFallDistance(20);
                    main.getGameManager().runEvent(CEvent.VOID, game, player);
                    main.getGameManager().death(player);
                }
                else{

                    Block bottom = player.getLocation().clone().add(0, -1, 0).getBlock();

                    if(bottom.getType() != Material.AIR) {
                        main.getGameManager().runBlockEvent(CEvent.WALK, game, player, bottom);
                    }

                }
            }

        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        event.setDeathMessage(null);

        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            event.getEntity().spigot().respawn();
        }, 1L);

    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        if(event.getPlayer().getWorld().getName().equalsIgnoreCase("world"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        if(victim.getType() == EntityType.PLAYER)
        {
            Player victimP = (Player) victim;
            if(main.getGameManager().isPlayerInGame(victimP).size()!=0)
            {

                CYGame game = main.getGameManager().getCurrentGame(victimP);

                if(!game.hasStarted())
                {
                    event.setCancelled(true);
                    return;
                }

                if(event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK
                    && game.canDamageFall())
                {
                    event.setCancelled(true);
                    return;
                }

                if(victimP.getHealth() - event.getDamage() <= 0)
                {
                    event.setCancelled(true);
                    victimP.setGameMode(GameMode.SPECTATOR);
                    main.getGameManager().death(victimP.getPlayer());
                    main.getGameManager().runEvent(CEvent.DEATH, game, victimP);

                    return;
                }


            }else {


                if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    event.setCancelled(true);
                    victimP.teleport(new Location(victimP.getWorld(), 0, 20, 0));

                }
            }

            if (victimP.getHealth() - event.getDamage() <= 0) {
                event.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String[] args = event.getMessage().split(" ");

        if((args[0].contains("worldedit") || args[0].contains("//") ) && player.isOp())
        {
            event.setCancelled(true);
        }

        if(args[0].equalsIgnoreCase("/ver"))
        {
            event.setCancelled(true);
        }

        if(args[0].equalsIgnoreCase("/tell"))
        {
            event.setCancelled(true);
        }

        if(args[0].equalsIgnoreCase("/w"))
        {
            event.setCancelled(true);
        }

        if(args[0].equalsIgnoreCase("?"))
        {
            event.setCancelled(true);
        }

        if(args[0].equalsIgnoreCase("/me"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity victim = event.getEntity();

        if(damager.getType() == EntityType.ARROW) {
            Player victimP = (Player) victim;
            Player shooter = (Player) ((Projectile)event.getDamager()).getShooter();

            if(main.getGameManager().isPlayerInGame(victimP).size()!=0)
            {
                CYGame game = main.getGameManager().getCurrentGame(victimP);
                if(!game.canFight())
                {
                    event.setCancelled(true);
                }

                if(game.sameTeam(victimP.getPlayer(), shooter) && game.hasTeamSystem())
                {
                    damager.sendMessage("§cVous êtes dans la même équipe !");
                    event.setCancelled(true);
                }

                if(victimP.getHealth() - event.getDamage() <= 0)
                {
                    main.getGameManager().runEvent(CEvent.KILL, game, shooter);
                }
            }
        }

        if(damager.getType() == EntityType.PLAYER && victim.getType() == EntityType.PLAYER)
        {
            Player victimP = (Player) victim;
            if(main.getGameManager().isPlayerInGame(victimP).size()!=0)
            {
                CYGame game = main.getGameManager().getCurrentGame(victimP);
                if(!game.canFight())
                {
                    event.setCancelled(true);
                }

                if(game.sameTeam(victimP.getPlayer(), (Player) damager) && game.hasTeamSystem())
                {
                    damager.sendMessage("§cVous êtes dans la même équipe !");
                    event.setCancelled(true);
                }

                if(victimP.getHealth() - event.getDamage() <= 0)
                {
                    main.getGameManager().runEvent(CEvent.KILL, game, ((Player) damager).getPlayer());
                }
            }
        }


    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && event.getPlayer().getWorld().getName().equalsIgnoreCase("world")){
            event.setCancelled(true);
        }

        if (event.getPlayer().getGameMode() == GameMode.SPECTATOR && event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))
            event.setCancelled(true);
    }

}
