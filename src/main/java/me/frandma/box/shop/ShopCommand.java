package me.frandma.box.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Inventory gui = Bukkit.createInventory(null, 27, "Shop");
        gui.setItem(11, item(Material.LEATHER_HELMET, "§9Upgrades", null));
        gui.setItem(13, item(Material.BOOK, "§9Perks", null));
        gui.setItem(15, item(Material.WOODEN_PICKAXE, "§9Mines", null));
        ((Player) sender).openInventory(gui);
        return true;
    }

    private ItemStack item(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
