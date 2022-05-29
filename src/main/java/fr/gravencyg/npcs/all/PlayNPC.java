package fr.gravencyg.npcs.all;

import fr.gravencyg.CYG;
import fr.gravencyg.menus.all.PlayMenu;
import fr.gravencyg.npcs.NPC;
import org.bukkit.entity.Player;

public class PlayNPC extends NPC {

    public PlayNPC(CYG main) {
        super(main, "Jouer", -29.47, 22, 1.4, -90f, 0f);
    }

    public String skinUUID() {
        return "4ddcd747-679e-4fc1-93fd-04904e0a0260";
    }

    @Override
    public String hologramMessage() { return "Jouer à un Jeu"; }

    @Override
    public double[] colliderOffset() { return new double[]{0.5, 0}; }

    @Override
    public void onClick(Player player) {
        main.getMenuManager().open(player, PlayMenu.class);
    }

}
