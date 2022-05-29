package fr.gravencyg.manager;

import fr.gravencyg.CYG;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CEvent;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.*;

public class CYGCompilerManager {

    private CYG main;
    public CYGCompilerManager(CYG main) { this.main = main; }

    private List<ActionProcess> processedBlocks = new ArrayList<>();

    public void runEventByBlock(Player player, String uuid, Block block) {
        // for each event of the current uuid map
        for(Map.Entry<String, String> e : main.getDataManager().getStorageByUUID(uuid).getPlot(player.getWorld().getName()).getEvents().entrySet()){
            // found an event
            if (e.getValue().equalsIgnoreCase(LocationUtils.fromLocToString(block.getLocation())))
            {
                runEvent(player, uuid, e.getKey(), true, false, player.getLocation().clone().add(0, -1, 0).getBlock(), Material.LAPIS_BLOCK);
            }
        }
    }

    public void runEvent(Player executor, String ownerPlotUUID, String eventName, boolean unitTestMode, boolean isAffectingAllPlayers, Block blk, Material material) {

        Block startingBlock = LocationUtils.fromStringToLoc(ownerPlotUUID, main.getDataManager()
                .getStorageByUUID(ownerPlotUUID)
                .getPlot(executor.getWorld().getName())
                .getEvents().get(eventName))
                .getBlock();

        if (unitTestMode) {
            broadcastEvent(ownerPlotUUID, ChatColor.GRAY + "Execution de l'evenement " + ChatColor.BLUE + eventName + "()");
        }

        List<Player> players = new ArrayList<>();

        if (isAffectingAllPlayers) {
            Bukkit.getOnlinePlayers().stream().filter(p -> p.getWorld().getName().contains(ownerPlotUUID)).forEach(players::add);
        } else {
            players.add(executor);
        }

        List<BlockFace> processusDirection = new ArrayList<>();

        for (BlockFace bf : BlockFace.values()) {
            if (bf.equals(BlockFace.EAST) || bf.equals(BlockFace.NORTH) || bf.equals(BlockFace.WEST) || bf.equals(BlockFace.SOUTH)) {
                Block b = startingBlock.getRelative(bf.getOppositeFace());
                if(b.getType() != Material.AIR) processusDirection.add(bf);
            }
        }

        for(BlockFace uniqueBf : processusDirection)
        {
            List<Block> blocks = new ArrayList<>();
            blocks.add(startingBlock);

            for(int i = 0; i < 100; i++)
            {

                Block lastBlock = blocks.get(blocks.size()-1);
                Block b = lastBlock.getRelative(uniqueBf.getOppositeFace());

                if(b.getType() != Material.AIR && b.getType() != Material.LAPIS_BLOCK && !blocks.contains(b))
                {
                    int iterations = 1;

                    if(b.getType() == Material.LEGACY_WOOD_STEP)
                        iterations = 10; // 1 S

                    if(b.getType() == Material.LEGACY_STEP)
                        iterations = 100; // 10 S

                    if(b.getType() == Material.LEGACY_STONE)
                        iterations = 600; // 10 S

                    for(int f = 0; f < iterations; f++) {
                        blocks.add(b);
                    }

                }

            }

            processedBlocks.add(new ActionProcess(players, blocks, blk, material));

        }

    }



    public void broadcastEvent(String ownerPlotUUID, String message) {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if(player.getWorld().getName().equalsIgnoreCase(ownerPlotUUID))
            {
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 9, 9);
                player.sendMessage(ChatColor.RESET + "\n\n>>> " + message + ChatColor.RESET + " <<<\n\n");
            }
        }
    }

    public List<ActionProcess> getProcessedBlocks() {
        return processedBlocks;
    }

}
