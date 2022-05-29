package fr.gravencyg.actions.basic;

import fr.gravencyg.CYG;
import fr.gravencyg.actions.Action;
import fr.gravencyg.model.ActionProcess;
import fr.gravencyg.utils.CLevel;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;

public class MusicAction extends Action {

    public MusicAction(CYG main) {
        super(main, CLevel.LEVEL2);
    }

    @Override
    public String title() {
        return ChatColor.BLUE + "PlayNote()";
    }

    @Override
    public String lore() {
        return ChatColor.GRAY + "Jouer une note de musique";
    }

    @Override
    public void onExecute(Player player, Block block, ActionProcess actionProcess) {
        BlockState bs = block.getState();
        if (bs instanceof NoteBlock) {
            NoteBlock b = (NoteBlock) bs;
            player.playNote(player.getLocation(), b.getInstrument(), b.getNote());
        }
    }
}
