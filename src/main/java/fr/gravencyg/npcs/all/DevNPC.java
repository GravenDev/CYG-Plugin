package fr.gravencyg.npcs.all;

import fr.gravencyg.CYG;
import fr.gravencyg.menus.plot.PlotMenu;
import fr.gravencyg.npcs.NPC;
import org.bukkit.entity.Player;

public class DevNPC extends NPC {

    public DevNPC(CYG main) {
        super(main, "Développer", -21.530, 22, 9.495, -113.7f, 3.7f);
    }

    public String skinUUID() {
        return "a3208407-feaf-470b-ba15-0b7e67cb0ac9";
    }

    public String hologramMessage() { return "Rejoindre ta parcelle"; }

    @Override
    public double[] colliderOffset() { return new double[]{0.3, -0.2}; }

    @Override
    public void onClick(Player player) {
        main.getMenuManager().open(player, PlotMenu.class);
    }

}
