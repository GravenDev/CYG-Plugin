package fr.gravencyg.items;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorManager {

    private Map<Material, Armor> armors = new HashMap<>();

    public ArmorManager() {

        armors.put(Material.LEATHER_HELMET, Armor.HELMET);
        armors.put(Material.CHAINMAIL_HELMET, Armor.HELMET);
        armors.put(Material.IRON_HELMET, Armor.HELMET);
        armors.put(Material.GOLDEN_HELMET, Armor.HELMET);
        armors.put(Material.DIAMOND_HELMET, Armor.HELMET);
        armors.put(Material.SKELETON_SKULL, Armor.HELMET);

        armors.put(Material.LEATHER_CHESTPLATE, Armor.CHESTPLATE);
        armors.put(Material.CHAINMAIL_CHESTPLATE, Armor.CHESTPLATE);
        armors.put(Material.IRON_CHESTPLATE, Armor.CHESTPLATE);
        armors.put(Material.GOLDEN_CHESTPLATE, Armor.CHESTPLATE);
        armors.put(Material.DIAMOND_CHESTPLATE, Armor.CHESTPLATE);

        armors.put(Material.LEATHER_LEGGINGS, Armor.LEGGINGS);
        armors.put(Material.CHAINMAIL_LEGGINGS, Armor.LEGGINGS);
        armors.put(Material.IRON_LEGGINGS, Armor.LEGGINGS);
        armors.put(Material.GOLDEN_LEGGINGS, Armor.LEGGINGS);
        armors.put(Material.DIAMOND_LEGGINGS, Armor.LEGGINGS);

        armors.put(Material.LEATHER_BOOTS, Armor.BOOTS);
        armors.put(Material.CHAINMAIL_BOOTS, Armor.BOOTS);
        armors.put(Material.IRON_BOOTS, Armor.BOOTS);
        armors.put(Material.GOLDEN_BOOTS, Armor.BOOTS);
        armors.put(Material.DIAMOND_BOOTS, Armor.BOOTS);

    }

    public void addItem(Player player, ItemStack item){

        Material mat = item.getType();

        if(armors.containsKey(mat))
        {

            switch(armors.get(mat))
            {
                case HELMET: player.getInventory().setHelmet(item); break;
                case CHESTPLATE: player.getInventory().setChestplate(item); break;
                case LEGGINGS: player.getInventory().setLeggings(item); break;
                case BOOTS: player.getInventory().setBoots(item); break;
                default: break;
            }

            return;
        }

        player.getInventory().addItem(item);

    }

}