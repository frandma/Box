package me.frandma.box.sell;

import me.frandma.box.user.User;
import me.frandma.box.user.UserManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SellCommand implements CommandExecutor {

    private List<Material> sellableItems = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;
        if (!(sender instanceof Player)) return true;
        User u = UserManager.getUser(((Player) sender).getUniqueId());
        switch (args[0]) {
            case "all":

                return true;
            case "hand":

                return true;
        }
        return true;
    }
    private void setup() {

    }
}
