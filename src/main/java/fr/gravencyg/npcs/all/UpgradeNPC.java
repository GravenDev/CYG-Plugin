package fr.gravencyg.npcs.all;

import fr.gravencyg.CYG;
import fr.gravencyg.menus.all.UpgradeMenu;
import fr.gravencyg.npcs.NPC;
import org.bukkit.entity.Player;

public class UpgradeNPC extends NPC {

    public UpgradeNPC(CYG main) {
        super(main, "Améliorer", -21.570, 22, -6.535f, -63.2f, 1f);
    }

    public String skinUUID() {
        return "e9c3d931-16ac-4683-aaf4-35707bac4948";
    }

    @Override
    public String hologramMessage() { return "Débloquer des niveaux"; }

    @Override
    public double[] colliderOffset() { return new double[]{0.3, 0.3}; }

    @Override
    public void onClick(Player player) {
        main.getMenuManager().open(player, UpgradeMenu.class);
    }

}