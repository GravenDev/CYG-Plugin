package fr.gravencyg.actions.team;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public enum Team {

    WHITE(0, ChatColor.WHITE, "Blanc"),
    ORANGE(1, ChatColor.GOLD, "Orange"),
    LIGHT_BLUE(3, ChatColor.BLUE, "Bleu Clair"),
    YELLOW(4, ChatColor.YELLOW, "Jaune"),
    LIGHT_GREEN(5, ChatColor.GREEN, "Vert"),
    PINK(6, ChatColor.getByChar("d"), "Rose"),
    DARK_GRAY(7, ChatColor.DARK_GRAY, "Gris foncé"),
    LIGHT_GRAY(8, ChatColor.GRAY, "Gris clair"),
    CYAN(9, ChatColor.LIGHT_PURPLE, "Cyan"),
    DARK_PURPLE(10, ChatColor.DARK_PURPLE, "Violet"),
    BLUE(11, ChatColor.BLUE, "Bleu"),
    RED(14, ChatColor.RED, "Rouge"),
    BLACK(15, ChatColor.BLACK, "Noir");

    private int data;
    private ChatColor color;
    private String name;

    static Map<Integer, Team> teamMap = new HashMap<>();

    static {
        for(Team team: values())
        {
            teamMap.put(team.getData(), team);
        }
    }

    Team(int data, ChatColor color, String name)
    {
        this.data = data;
        this.color = color;
        this.name = name;
    }

    private Integer getData() {
        return data;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public static Team getByWoolData(int data){
        return teamMap.get(data);
    }
}
