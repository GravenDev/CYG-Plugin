package fr.gravencyg.actions;

import fr.gravencyg.CYG;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class Action {

    protected CYG main;
    private int slot;
    private CLevel minLevel;

    public Action(CYG main, CLevel level) {
        this.main = main;
        this.slot = 53;
        this.minLevel = level;
    }

    public CLevel getRequireLevel(){ return minLevel; }

    public abstract String title();

    public abstract String lore();

    public abstract void onExecute(Player player, Block block, ActionProcess actionProcess);

    public void setSlot(int slot) { this.slot = slot; }

    public int getSlot(){ return slot; }


}
