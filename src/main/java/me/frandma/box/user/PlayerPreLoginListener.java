package me.frandma.box.user;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if (UserManager.getUser(e.getUniqueId()) != null) return;
        new User(e.getUniqueId(), null, 0, true);
    }
}
