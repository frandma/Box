package me.frandma.box.user;

import lombok.experimental.UtilityClass;
import me.frandma.box.BoxPlugin;
import me.frandma.box.arena.ArenaManager;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class UserManager {

    private List<User> userList = new ArrayList<>();

    private Connection c;

    public void setup() {
        File dataFolder = new File(BoxPlugin.getInstance().getDataFolder(), "users.db");
        if (!dataFolder.exists()){
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().info("File write error: users.db");
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            try (PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS users (" +
                    "uuid varchar(255) PRIMARY KEY, " +
                    "ownedarenas varchar(255) NOT NULL, " +
                    "coins varchar(255) NOT NULL);")) {
                ps.execute();
            }
            try (PreparedStatement ps = c.prepareStatement("SELECT * FROM users;")) {
                ResultSet rs = ps.executeQuery();
                Bukkit.getLogger().info("loading users");
                while (rs.next()) {
                    new User(UUID.fromString(rs.getString(1)), ArenaManager.arenasFromString(rs.getString(2)), rs.getInt(3), false);
                }
                Bukkit.getLogger().info("loaded users");
            }
        } catch (SQLException | ClassNotFoundException e) {
            Bukkit.getLogger().info(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return c;
    }

    protected void addUserToList(User user) {
        userList.add(user);
    }

    protected void addUserToDatabase(User user) {
        try (PreparedStatement ps = c.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?);")) {
            ps.setString(1, user.getUuid().toString());
            ps.setInt(2, user.getCoins());
            ps.executeUpdate();
        } catch (SQLException e){
            Bukkit.getLogger().info(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public User getUser(UUID uuid) {
        for (User user : userList) {
            if (!user.getUuid().equals(uuid)) continue;
            return user;
        }
        return null;
    }

    public List<User> getUserList() {
        return userList;
    }

}
