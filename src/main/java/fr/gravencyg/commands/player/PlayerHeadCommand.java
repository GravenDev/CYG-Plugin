package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class PlayerHeadCommand extends TCommand {

    public PlayerHeadCommand(CYG main) {
        super(main, CLevel.LEVEL6, false, false, true, false);
    }

    @Override
    public void onExecute(Player player, String[] args) {

        String target = player.getName();

        if(args.length == 1)
        {
            target = args[0];
        }

        player.getInventory().addItem(getHead(target));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 8, 3);
    }

    public static ItemStack getHead(String name) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("§eCustom head");
        skull.setLore(lore);
        skull.setOwner(name);
        item.setItemMeta(skull);
        return item;
    }

}

