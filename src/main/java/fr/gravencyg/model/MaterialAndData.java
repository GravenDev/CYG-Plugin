package fr.gravencyg.model;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class MaterialAndData {

    private Material material;
    private byte data;

    public MaterialAndData(Material material, byte data)
    {
        this.material = material;
        this.data = data;
    }

    public byte getData() {
        return data;
    }

    public Material getMaterial(){
        return material;
    }
}
