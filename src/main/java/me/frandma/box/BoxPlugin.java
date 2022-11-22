package me.frandma.box;

import lombok.Getter;
import lombok.Setter;
import me.frandma.box.arena.ArenaCommand;
import me.frandma.box.arena.ArenaManager;
import me.frandma.box.listeners.InventoryClickListener;
import me.frandma.box.sell.SellCommand;
import me.frandma.box.user.PlayerPreLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
@Setter
public final class BoxPlugin extends JavaPlugin {

    private static BoxPlugin instance;

    private static FileConfiguration items = YamlConfiguration.loadConfiguration(new File(getInstance().getFile(), "items.yml"));

    @Override
    public void onEnable() {
        instance = this;
        ArenaManager.setup();
        registerCommands();

    }

    public static BoxPlugin getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("sell").setExecutor(new SellCommand());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPreLoginListener(), this);
    }

}
