package me.frandma.box.arena;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import me.frandma.box.arena.Arena;
import me.frandma.box.arena.ArenaManager;
import me.frandma.box.utils.Cube;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (args.length == 0) return false;
        switch (args[0]) {
            case "create":
                if (args.length < 4 && !isInteger(args[2])) return false;

                Material material = p.getInventory().getItemInMainHand().getType();
                if (!material.isBlock()) {
                    p.sendMessage("§cYou must be holding a block to create an arena.");
                    return true;
                }

                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                BlockVector3 min = null;
                BlockVector3 max = null;
                try {
                    Region region = worldEdit.getSession(p).getSelection();
                    min = region.getMinimumPoint();
                    max = region.getMaximumPoint();
                } catch (IncompleteRegionException e) {
                    Bukkit.getLogger().info(e.getClass().getName() + ": " + e.getMessage());
                }

                if (min == null && max == null) {
                    p.sendMessage("§cUse WorldEdit to add selections for the arena.");
                }
                Cube c = new Cube(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
                new Arena(args[1], p.getWorld(), c, material, Integer.parseInt(args[2]), Integer.parseInt(args[3]), true);
                p.sendMessage("Created new arena §b" + args[1] + "§f.");
                return true;
            case "list":
                for (Arena arena : ArenaManager.getArenaList()) {
                    p.sendMessage(arena.toString());
                }
                return true;
            case "delete":
                if (args.length < 2) return false;
                Arena arena = ArenaManager.getArena(args[1]);
                if (arena == null) {
                    p.sendMessage("§cArena not found.");
                    return true;
                }
                ArenaManager.deleteArena(arena);
                p.sendMessage("Arena §b" + arena.getName() + "§f was deleted");
            default:
                return false;
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

}
