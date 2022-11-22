package me.frandma.box.shop;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@UtilityClass
public class Shop {
    private Inventory mainInventory = Bukkit.createInventory(null, 27, "Shop");
    private Inventory upgradesInventory = Bukkit.createInventory(null, 27, "Shop");

    public void setup() {
        mainInventory.setItem(11, item(Material.LEATHER_HELMET, "§9Upgrades", null));
        mainInventory.setItem(13, item(Material.BOOK, "§9Perks", null));
        mainInventory.setItem(15, item(Material.WOODEN_PICKAXE, "§9Mines", null));

        upgradesInventory.setItem(10, item(Material.DIAMOND_SWORD));
    }

    public Inventory getMainInventory() {
        return mainInventory;
    }

    private ItemStack item(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
