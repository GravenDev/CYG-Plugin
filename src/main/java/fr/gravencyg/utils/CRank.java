package fr.gravencyg.utils;

import org.bukkit.ChatColor;

public enum CRank {
    JOUEUR(ChatColor.GRAY),
    PREMIUM(ChatColor.GOLD),
    YOUTUBEUR(ChatColor.RED),
    BUILDER(ChatColor.BLUE),
    MODÉRATION(ChatColor.BLUE),
    ADMIN(ChatColor.RED);

    private ChatColor color;

    CRank(ChatColor color){
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }

    public Object getName() { return getSigle() + name().substring(1).toLowerCase(); }

    public Object getSigle() { return name().toUpperCase().substring(0, 1); }

    public boolean isNotMod() {
        return this != CRank.MODÉRATION && this != CRank.ADMIN;
    }

    public boolean isMod() {
        return this == CRank.MODÉRATION || this == CRank.ADMIN;
    }
}
