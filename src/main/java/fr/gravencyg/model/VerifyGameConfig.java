package fr.gravencyg.model;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class VerifyGameConfig {

    private boolean isVerify = false;
    private String icon = "DIAMOND_SWORD";

    private int slot = 0;

    public VerifyGameConfig() {
        this.icon = icon;
    }

    public int getSlot(){ return slot; }

    public Material getMaterial(){
        for(Material mat : Material.values())
        {
            if(mat.name().equalsIgnoreCase(icon.toUpperCase()))
            {
                return mat;
            }
        }

        return Material.STONE;
    }

    public boolean isVerify() { return this.isVerify; }

    public void setVerify(boolean verify) { this.isVerify = verify;}

    public void setIcon(String material) { this.icon = material; }

    public void setSlot(int numberSlot) { this.slot = numberSlot; }
}
