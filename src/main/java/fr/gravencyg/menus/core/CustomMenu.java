package fr.gravencyg.menus.core;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface CustomMenu {
	
	String name();
	
	void contents(Player player, Inventory inv);
	
	void onClick(Player player, Inventory inv, ItemStack current, int slot);
	
	int getSize();

}
