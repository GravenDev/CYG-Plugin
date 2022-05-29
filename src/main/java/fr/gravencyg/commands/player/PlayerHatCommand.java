package fr.gravencyg.commands.player;

import fr.gravencyg.CYG;
import fr.gravencyg.commands.TCommand;
import fr.gravencyg.utils.CLevel;
import fr.gravencyg.utils.CRank;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerHatCommand extends TCommand {

    public PlayerHatCommand(CYG main) {
        super(main, CLevel.LEVEL1,false, true, false, true);
    }

    public void onExecute(Player player, String[] args) {

        if(main.getDataManager().getRank(player) != CRank.PREMIUM && main.getDataManager().getRank(player).isNotMod())
        {
            player.sendMessage("§fUniquement pour les §ePremiums§r et le §9Staff");
            return;
        }

        ItemStack inHand = player.getInventory().getItemInMainHand();

        if(inHand.getType() != Material.AIR)
        {
            player.sendMessage("§eNous avons remplacé votre tête par §6"+inHand.getType().name()+"§e !");
            player.getInventory().setHelmet(inHand);
        }

    }

}
