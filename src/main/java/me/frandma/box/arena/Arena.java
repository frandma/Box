package me.frandma.box.arena;

import me.frandma.box.BoxPlugin;
import me.frandma.box.utils.Cube;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;

public class Arena {

    private String name;
    private World world;
    private Cube cube;

    private Material material;
    private int price;
    private int seconds;
    private BukkitTask bukkitTask;

    public Arena(String name, World world, Cube cube, Material material, int price,  int seconds, boolean intoTheDatabase) {
        this.name = name;
        this.world = world;
        this.cube = cube;
        this.material = material;
        this.price = price;
        this.seconds = seconds;
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                BoundingBox box = new BoundingBox(cube.getX1(), cube.getY1(), cube.getZ1(), cube.getX2(), cube.getY2(), cube.getZ2());
                for (int i = cube.getX1(); i <= cube.getX2();i++) {
                    for (int j = cube.getY1(); j <= cube.getY2(); j++) {
                        for (int k = cube.getZ1(); k <= cube.getZ2();k++) {
                            Block block = world.getBlockAt(i,j,k);
                            block.setType(material);
                        }
                    }
                }
            }
        }.runTaskTimer(BoxPlugin.getInstance(), 0, seconds * 20);
        ArenaManager.addArenaToList(this);
        if (!intoTheDatabase) return;
        ArenaManager.addArenaToDatabase(this);
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public Cube getCube() {
        return cube;
    }

    public Material getMaterial() {
        return material;
    }

    public int getPrice() {
        return price;
    }

    public int getSeconds() {
        return seconds;
    }

    public BukkitTask getBukkitTask() {
        return bukkitTask;
    }

    public String toString() {
        return getName() + ", " + getWorld().getName() + ", " + getCube().toString() + ", " + getMaterial() + ", " + getSeconds();
    }
}
