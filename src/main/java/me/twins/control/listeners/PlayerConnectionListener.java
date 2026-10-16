package me.twins.control.listeners;

import me.twins.control.TwinsControl;
import me.twins.control.manager.ControlManager;
import me.twins.control.model.ControlledSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    private final TwinsControl plugin;
    private final ControlManager controlManager;

    public PlayerConnectionListener(TwinsControl plugin) {
        this.plugin = plugin;
        this.controlManager = plugin.getControlManager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (controlManager.isControlled(player.getUniqueId())) {
            // Kontrol noktasına tekrar ışınla ve efektleri tazele
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (controlManager.hasControlLocation()) {
                    player.teleport(controlManager.getControlLocation());
                }
                controlManager.applyControlVisuals(player);

                // BossBar'a tekrar ekle
                ControlledSession session = controlManager.getSession(player.getUniqueId());
                if (session != null && session.getBossBar() != null) {
                    session.getBossBar().addPlayer(player);
                }
            }, 10L);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        controlManager.handleQuit(e.getPlayer());
    }
}
