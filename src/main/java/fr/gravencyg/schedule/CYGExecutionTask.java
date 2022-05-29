package fr.gravencyg.schedule;

import fr.gravencyg.CYG;
import fr.gravencyg.game.CYGame;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.model.Storage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CYGExecutionTask extends BukkitRunnable {

    private CYG main;
    private List<Block> lapisPreview = new ArrayList<>();

    public CYGExecutionTask(CYG main){
        this.main = main;
    }

    @Override
    public void run() {


        for(Block lapis : lapisPreview)
        {
            lapis.setType(Material.AIR);
        }

        lapisPreview.clear();

        main.getGameManager().playLoop();

        for(World world : Bukkit.getWorlds())
        {
            for(Entity entity : world.getEntities())
            {
                if(entity.getType() == EntityType.ENDER_DRAGON) {
                    entity.remove();
                }
                if(entity.getType() == EntityType.BOAT) {
                    entity.remove();
                }

            }
        }

        if( main.getCompilerManager().getProcessedBlocks().size() != 0) {

            List<ActionProcess> actionProcessesToRemove = new ArrayList<>();


            for (ActionProcess actionProcess : main.getCompilerManager().getProcessedBlocks()) {

                if (actionProcess.getProcessedBlocks().size() >= 1) {



                    Block currentBlock = actionProcess.getProcessedBlocks().get(0);

                    if (currentBlock.getType() != Material.LAPIS_BLOCK) {
                        main.getActionBlocks().execute(actionProcess, currentBlock);

                        Block top = currentBlock.getLocation().add(0, 1, 0).getBlock();

                        if (top.getType() == Material.AIR) {
                            top.setType(actionProcess.getMaterial());
                            lapisPreview.add(top);
                        }

                        // remove first action from list
                        actionProcess.getProcessedBlocks().remove(currentBlock);

                        // remove action list to processed blocks
                        if (actionProcess.getProcessedBlocks().size() == 0) {
                            actionProcessesToRemove.add(actionProcess);

                            Storage storage = main.getDataManager().getStorageByUUID(actionProcess.getPlayers().get(0).getWorld().getName());
                            main.getGameManager().removeLoopStorage(storage);
                        }
                    }
                    else{
                        actionProcess.getProcessedBlocks().remove(currentBlock);
                    }


                }
            }

            actionProcessesToRemove.forEach(main.getCompilerManager().getProcessedBlocks()::remove);
        }

    }

}
