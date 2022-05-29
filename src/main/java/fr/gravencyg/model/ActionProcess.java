package fr.gravencyg.model;

import fr.gravencyg.utils.CEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionProcess {

    private List<Player> players;
    private List<Block> processedBlocks;
    private Block block;
    private Material material;

    public ActionProcess(List<Player> players, List<Block> processedBlocks, Block block, Material material)
    {
        this.players = players;
        this.processedBlocks = processedBlocks;
        this.block = block;
        this.material = material;
    }

    public Material getMaterial() { return material; }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Block> getProcessedBlocks() {
        return processedBlocks;
    }

    public Block getBlock() {
        return block;
    }

}
