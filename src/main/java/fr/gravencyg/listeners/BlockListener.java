package fr.gravencyg.listeners;

import fr.gravencyg.CYG;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.manager.CYGHologramManager;
import fr.gravencyg.menus.all.*;
import fr.gravencyg.menus.locations.LocationSelectMenu;
import fr.gravencyg.menus.messages.MessageSelectMenu;
import fr.gravencyg.model.MaterialAndData;
import fr.gravencyg.model.Plot;
import fr.gravencyg.model.Storage;
import fr.gravencyg.utils.CEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

    private CYG main;

    public BlockListener(CYG main) {
        this.main = main;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        // DISABLE PLACING BLOCK IN THE HUB
        if(block.getWorld().getName().equalsIgnoreCase("world") && !player.getName().equalsIgnoreCase("GravenYT") && !player.getName().equalsIgnoreCase("FragZzH"))
        {
            event.setCancelled(true);
        }

        Plot plot = main.getDataManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());

        if(plot != null ) {
            if (main.getGameManager().isPlayerInGame(player).size() != 0) {
                CYGame game = main.getGameManager().getCurrentGame(player);
                if (!game.canBuild()) {
                    event.setCancelled(true);
                } else {
                    game.getPlacedBlocks().add(block);
                    main.getGameManager().runBlockEvent(CEvent.PLACE, game, player, block);
                }
            } else if (main.getGameManager().isPlayerInGame(player).size()==0 && !block.getWorld().getName().contains(player.getUniqueId().toString())
                    && !plot.getFriends().contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getWorld().getName().equalsIgnoreCase("world") && !player.getName().equalsIgnoreCase("GravenYT")  && !player.getName().equalsIgnoreCase("FragZzH")) {
            event.setCancelled(true);
            return;
        }


        Plot plot = main.getDataManager().getStorageByUUID(player.getWorld().getName()).getPlot(player.getWorld().getName());

        if(plot != null) {

            if (main.getGameManager().isPlayerInGame(player).size()!=0) {
                CYGame game = main.getGameManager().getCurrentGame(player);
                if (!game.canBuild()) {
                    event.setCancelled(true);
                } else {
                    game.getBreakBlocks().put(block.getLocation(), new MaterialAndData(block.getType(), block.getData()));
                    main.getGameManager().runBlockEvent(CEvent.BREAK, game, player, block);
                }

             } else if (main.getGameManager().isPlayerInGame(player).size()==0 && !block.getWorld().getName().contains(player.getUniqueId().toString())
                && !plot.getFriends().contains(player.getName())) {
            event.setCancelled(true);
        }

            if (block.getType() == Material.LAPIS_BLOCK) {
                World world = block.getWorld();

                // the block breaker is the owner of the plot
                if (world.getName().contains(player.getUniqueId().toString())) {

                    main.getDataManager().removeEvent(player, block);
                    main.getDataManager().save(player);

                    CYGHologramManager hologramManager = main.getHologramManager();
                    hologramManager.removeHologram(block);
                }
            }
        }

    }

    @EventHandler
    public void onSign(BlockPistonExtendEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onSign(SignChangeEvent event){
        for(int i = 0; i < event.getLines().length; i++) {
            event.setLine(i, event.getLine(i).replace("&", "§"));
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onDispense(BlockSpreadEvent event){
        if(event.getBlock().getType() == Material.MYCELIUM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClickBlock(PlayerInteractEvent event) {

        Player player = event.getPlayer();

            if(player.getWorld().getName().equalsIgnoreCase("world")) return;


            Storage storage = main.getDataManager().getStorageByUUID(player.getWorld().getName());
            Plot plot = storage.getPlot(player.getWorld().getName());

            if(plot != null) {

                boolean isNotFriendOrOwner = !plot.getFriends().contains(player.getName()) && !storage.getDisplayName().equalsIgnoreCase(player.getName());

                if(!isNotFriendOrOwner && player.getInventory().getItemInMainHand().getType() == Material.GOLDEN_HOE && event.getClickedBlock().getType() != Material.AIR)
                {

                    Block block = event.getClickedBlock();
                    main.getWorldEditManager().setPoint(player, block.getLocation());
                    event.setCancelled(true);
                    return;
                }


                if(event.getAction() == Action.LEFT_CLICK_AIR && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    main.getGameManager().runEvent(CEvent.LEFT_CLICK, storage, player);
                }

                if(event.getAction() == Action.RIGHT_CLICK_AIR) {
                    main.getGameManager().runEvent(CEvent.RIGHT_CLICK, storage, player);
                }

                if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() != Material.AIR) {

                    Block block = event.getClickedBlock();
                    if (block.getType() == Material.LAPIS_BLOCK) {

                        if (isNotFriendOrOwner) {
                            player.sendMessage("§cVous devez faire partit des développeurs de ce jeu");
                            event.setCancelled(true);
                            return;
                        }

                        main.getMenuManager().open(player, EventMenu.class);
                        main.getEventBlockCache().put(player, block);

                    }

                    if (block.getType() == Material.BEACON) {

                        event.setCancelled(true);

                        if (isNotFriendOrOwner) {
                            player.sendMessage("§cVous devez faire partit des développeurs de ce jeu");
                            event.setCancelled(true);
                            return;
                        }

                        if (!player.isSneaking()) {
                            main.getMenuManager().open(player, LocationSelectMenu.class);

                            main.getEventBlockCache().put(player, block);
                        }

                    }

                    if (block.getType() == Material.CHEST) {
                        Chest chest = (Chest) event.getClickedBlock().getState();

                        if (chest.getCustomName().equalsIgnoreCase(ChatColor.BLUE + "Stuff()")) {
                            if (isNotFriendOrOwner) {
                                player.sendMessage("§cVous devez faire partit des développeurs de ce jeu");
                                event.setCancelled(true);
                            }
                        }

                    }

                    if (block.getType() == Material.DROPPER) {
                        Dropper dropper = (Dropper) event.getClickedBlock().getState();

                        if (dropper.getCustomName().equalsIgnoreCase(ChatColor.BLUE + "DropItem()")) {
                            if (isNotFriendOrOwner) {
                                player.sendMessage("§cVous devez faire partit des développeurs de ce jeu");
                                event.setCancelled(true);
                            }
                        }

                    }

                    if (block.getType() == Material.BOOKSHELF || block.getType() == Material.SEA_LANTERN || block.getType() == Material.SOUL_SAND) {

                        event.setCancelled(true);

                        if (isNotFriendOrOwner) {
                            player.sendMessage("§cVous devez faire partit des développeurs de ce jeu");
                            event.setCancelled(true);
                            return;
                        }

                        if (!player.isSneaking()) {
                            main.getMenuManager().open(player, MessageSelectMenu.class);
                            main.getEventBlockCache().put(player, block);
                        }

                    }
                }


            }

            // DISABLE BLOCKS
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.getInventory().getItemInMainHand().getType() == Material.OAK_BOAT ||
                        player.getInventory().getItemInMainHand().getType() == Material.ACACIA_BOAT ||
                        player.getInventory().getItemInMainHand().getType() == Material.BIRCH_BOAT ||
                        player.getInventory().getItemInMainHand().getType() == Material.JUNGLE_BOAT ||
                        player.getInventory().getItemInMainHand().getType() == Material.DARK_OAK_BOAT ||
                        player.getInventory().getItemInMainHand().getType() == Material.SPRUCE_BOAT) {
                    event.setCancelled(true);
                }
            }

        // HUB DISABLE ITEMS
        if(player.getWorld().getName().equalsIgnoreCase("world"))
        {
            if(player.getInventory().getItemInMainHand().getType() == Material.SPLASH_POTION ||
                    player.getInventory().getItemInMainHand().getType() == Material.LAVA_BUCKET ||
                    player.getInventory().getItemInMainHand().getType() == Material.WATER_BUCKET) {
                event.setCancelled(true);
            }
        }

    }

}