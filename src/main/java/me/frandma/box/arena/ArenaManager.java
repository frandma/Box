package me.frandma.box.arena;

import lombok.experimental.UtilityClass;
import me.frandma.box.BoxPlugin;
import me.frandma.box.utils.Cube;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ArenaManager {

    private List<Arena> arenaList = new ArrayList<>();

    private Connection c;

    public void setup() {
        File dataFolder = new File(BoxPlugin.getInstance().getDataFolder(), "arenas.db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().info("File write error: arenas.db");
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            try (PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS arenas (" +
                    "name varchar(255) PRIMARY KEY, " +
                    "world varchar(255) NOT NULL, " +
                    "cube varchar(255) NOT NULL, " +
                    "material varchar(255) NOT NULL, " +
                    "price varchar(255) NOT NULL, " +
                    "seconds varchar(255) NOT NULL);")) {
                ps.execute();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM arenas;")) {
                ResultSet rs = ps.executeQuery();
                Bukkit.getLogger().info("loading arenas");
                while (rs.next()) {
                    new Arena(rs.getString(1), Bukkit.getWorld(rs.getString(2)), new Cube(rs.getString(3)), Material.valueOf(rs.getString(4)), rs.getInt(5), rs.getInt(6), false);
                }
                Bukkit.getLogger().info("loaded arenas");
            }
        } catch (SQLException | ClassNotFoundException e) {
            Bukkit.getLogger().info(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    protected void addArenaToList(Arena arena) {
        arenaList.add(arena);
    }

    protected void addArenaToDatabase(Arena arena) {
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO arenas VALUES (?, ?, ?, ?, ?, ?);")) {
            ps.setString(1, arena.getName());
            ps.setString(2, arena.getWorld().getName());
            ps.setString(3, arena.getCube().toString());
            ps.setString(4, arena.getMaterial().toString());
            ps.setInt(5, arena.getPrice());
            ps.setInt(6, arena.getSeconds());
            ps.executeUpdate();
        } catch (SQLException e){
            Bukkit.getLogger().info(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Arena getArena(String arenaName) {
        for (Arena arena : arenaList) {
            if (!arena.getName().equals(arenaName)) continue;
            return arena;
        }
        return null;
    }

    public List<Arena> getArenaList() {
        return arenaList;
    }

    public String listArenaToString(List<Arena> arenas) {
        List<String> arenaNames = new ArrayList<>();
        for (Arena arena : arenas) {
            arenaNames.add(arena.getName());
        }
        return String.join(";", arenaNames) + ";";
    }

    public String listArenaStringToString(List<String> arenaNames) {
        return String.join(";", arenaNames) + ";";
    }

    public List<Arena> arenasFromString(String arenaNames) {
        String[] arenaArray = arenaNames.split(";");
        List<Arena> arenas = new ArrayList<>();
        for (String arena : arenaArray) {
            arenas.add(getArena(arena));
        }
        return arenas;
    }

    public void deleteArena(Arena arena) {
        arena.getBukkitTask().cancel();
        if (arenaList.contains(arena)) {
            arenaList.remove(arena);
        }
        if (exists(arena.getName())) {
            try (PreparedStatement ps = c.prepareStatement("DELETE FROM arenas WHERE name = ?;")) {
                ps.setString(1, arena.getName());
                ps.executeQuery();
            } catch (SQLException e){
                Bukkit.getLogger().info(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public boolean exists(String name) {
        for (Arena arena : arenaList) {
            if (!arena.getName().equals(name)) continue;
            return true;
        }
        return false;
    }

}
