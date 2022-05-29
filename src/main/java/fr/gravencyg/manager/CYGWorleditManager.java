package fr.gravencyg.manager;

import fr.gravencyg.CYG;
import fr.gravencyg.items.ItemBuilder;
import fr.gravencyg.model.MaterialAndData;
import fr.gravencyg.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class CYGWorleditManager {

    private CYG main;

    private Map<Player, Location[]> points = new HashMap<>();
    private Map<Player, Map<Location, MaterialAndData>> lastAction = new HashMap<>();

    private HashMap<String, Material> materials = new HashMap<>();

    public CYGWorleditManager(CYG main)
    {
        this.main = main;

        for(Material material : Material.values())
        {
            materials.put(material.name().toLowerCase(), material);
        }
    }

    public void give(Player player)
    {
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3, 3);
        player.getInventory().addItem(new ItemBuilder(Material.GOLDEN_HOE, 1)
                .setName("§eMini Worledit")
                .addLoreLine("§7Clic droit/Clic Gauche")
                .addLoreLine("-> ").toItemStack());
    }

    public void setPoint(Player player, Location point)
    {
        if(!points.containsKey(player))
        {
            points.put(player, new Location[]{ point, point});
            point.getBlock().setType(Material.GLASS);
            player.sendMessage("§7[§eMiniWE§7]§r " + LocationUtils.fromLocToString2(point) + " est le 1er point !");
            return;
        }

        Location[] locations = points.get(player);
        locations[1] = point;
        point.getBlock().setType(Material.GLASS);

        player.sendMessage("§7[§eMiniWE§7]§r " + LocationUtils.fromLocToString2(point) + " est le 2eme point !");

    }
    public void set(Player player, String name) {
        set(player, name, 0);
    }

    public void set(Player player, String name, int id) {

        if(!points.containsKey(player))
        {
            player.sendMessage("§7[§eMiniWE§7]§r Aucun point n'a été placé ! ");
            return;
        }

        if(!materials.containsKey(name))
        {
            player.sendMessage("§7[§eMiniWE§7]§r Le materiel "+name.toUpperCase()+" n'existe pas ! ");
            return;
        }

        Location[] locs = points.get(player);
        Location corner1 = locs[0];
        Location corner2 = locs[1];
        Map<Location, MaterialAndData> blocks = getBlocks(corner1, corner2);

        if(blocks.size() > 15000)
        {
            player.sendMessage("§7[§eMiniWE§7]§r Attention la limite est de 15000 blocs ! (§c15000<"+blocks.size()+"§r)");
            return;
        }

        for(Map.Entry<Location, MaterialAndData> b : blocks.entrySet()) {
            b.getKey().getBlock().setType(materials.get(name.toLowerCase()));
            //b.getKey().getBlock().setData((byte) id);
        }

        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 2, 8);
        player.sendMessage("§7[§eMiniWE§7]§r Et voila ! Pas content ? /we undo ");


        points.remove(player);

        lastAction.put(player, blocks);

    }

    public void undo(Player player) {

        if(!lastAction.containsKey(player))
        {
            player.sendMessage("§7[§eMiniWE§7]§r Il n'y aucune dernière action ! ");
            return;
        }

        for(Map.Entry<Location, MaterialAndData> b: lastAction.get(player).entrySet())
        {
            b.getKey().getBlock().setType(b.getValue().getMaterial());
            //b.getKey().getBlock().setData(b.getValue().getData());
        }

        player.sendMessage("§7[§eMiniWE§7]§r On reviens au départ ");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 8);

        lastAction.remove(player);

    }

    public void unset(Player player) {

        if(!points.containsKey(player))
        {
            player.sendMessage("§7[§eMiniWE§7]§r Il n'y aucune selection ! ");
            return;
        }

        player.sendMessage("§7[§eMiniWE§7]§r Suppression de la selection ");
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 8);

        Location[] loc = points.get(player);
        loc[0].getBlock().breakNaturally();
        loc[1].getBlock().breakNaturally();

        points.clear();
    }

    public Map<Location, MaterialAndData> getBlocks(Location corner1, Location corner2){
        Map<Location, MaterialAndData> yourBlocks = new HashMap<>();

        int topBlockX = Math.max(corner1.getBlockX(), corner2.getBlockX());
        int topBlockY = Math.max(corner1.getBlockY(), corner2.getBlockY());
        int topBlockZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
        int bottomBlockX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int bottomBlockY = Math.min(corner1.getBlockY(), corner2.getBlockY());
        int bottomBlockZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                    Location loc = new Location(corner1.getWorld(), x, y, z);
                    yourBlocks.put(loc, new MaterialAndData(loc.getBlock().getType(), loc.getBlock().getData()));
                }
            }
        }

        return yourBlocks;
    }




}
