package fr.gravencyg.utils;

import org.bukkit.Material;

public enum CEvent {

    JOIN("onJoin", Material.PLAYER_HEAD, "Lorsqu'un joueur rejoint votre jeu", false, false),
    START("onStart", Material.SPECTRAL_ARROW, "Lorsque le jeu demarre", false, true),
    FINISH("onFinish", Material.DRAGON_EGG, "Lorsque le jeu s'arrete", false, true),
    QUIT("onQuit", Material.RED_BED, "Lorsqu'un joueur quitte le jeu", false, false),
    VOID("onFallVoid", Material.END_PORTAL_FRAME, "Lorsqu'un joueur tombe dans le vide", false, false),

    PLACE("onPlace", Material.STONE, "Lorsqu'un joueur place un bloc", true, false),
    BREAK("onBreak", Material.GOLDEN_PICKAXE, "Lorsqu'un joueur casse un bloc", true, false),
    WALK("onWalk", Material.IRON_BOOTS, "Lorsqu'un joueur marche sur un bloc", true, false),

    DEATH("onPlayerDeath", Material.SKELETON_SKULL, "Lorsqu'un joueur meurt", false, false),
    KILL("onPlayerKill", Material.DIAMOND_SWORD, "Lorsqu'un joueur tue un autre joueur", false, false),

    RIGHT_CLICK("onRightClick", Material.TORCH, "Lorsqu'un joueur fais un clic droit", false, false),
    LEFT_CLICK("onLeftClick", Material.LEVER, "Lorsqu'un joueur fais un clic gauche", false, false),

    LOOP("onLoop", Material.LEGACY_LEASH, "Repeter des évenements chaque seconde", false, true),;

    private String eventName;

    private String description;

    private boolean requireBlock;

    private boolean affectAllPlayers;

    private Material material;

    CEvent(String eventName, Material material, String description, boolean requireBlock, boolean affectAllPlayers){
        this.eventName = eventName;
        this.description = description;
        this.requireBlock = requireBlock;
        this.material = material;
        this.affectAllPlayers = affectAllPlayers;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAffectingAllPlayers() { return affectAllPlayers; }

    public String getEventName() {
        return eventName;
    }

    public Material getMaterial() { return material; }
}
