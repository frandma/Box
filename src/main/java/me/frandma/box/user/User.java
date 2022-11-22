package me.frandma.box.user;

import me.frandma.box.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class User {

    private final UUID uuid;
    public UUID getUuid() {
        return uuid;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    private List<Arena> ownedArenas = new ArrayList<>();
    public List<Arena> getOwnedArenas() {
        return ownedArenas;
    }
    public void setOwnedArenas(List<Arena> ownedArenas) {
        this.ownedArenas = ownedArenas;
    }

    private int coins;
    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }

    private ItemStack sword;
    private ItemStack axe;
    private ItemStack pickaxe;
    private ItemStack dHelmet;
    private ItemStack dChest;
    private ItemStack dLeg;
    private ItemStack dBoots;

    public User(UUID uuid, List<Arena> ownedArenas, int coins, boolean intoTheDatabase) {
        this.uuid = uuid;
        this.ownedArenas = ownedArenas;
        this.coins = coins;
        UserManager.addUserToList(this);
        if (!intoTheDatabase) return;
        UserManager.addUserToDatabase(this);
    }

}
