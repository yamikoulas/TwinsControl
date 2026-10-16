package me.twins.control.manager;

import me.twins.control.TwinsControl;
import me.twins.control.model.ControlledSession;
import me.twins.control.utils.ColorUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ControlManager {

    private final TwinsControl plugin;
    private final Map<UUID, ControlledSession> activeSessions = new ConcurrentHashMap<>();
    private BukkitTask tickerTask;

    public ControlManager(TwinsControl plugin) {
        this.plugin = plugin;
        loadSavedSessions();
        startTicker();
    }

    private FileConfiguration cfg() {
        return plugin.getConfig();
    }

    private LanguageManager lang() {
        return plugin.getLanguageManager();
    }

    private void loadSavedSessions() {
        List<String> list = cfg().getStringList("controlled");
        for (String entry : list) {
            try {
                UUID uuid = UUID.fromString(entry);
                Player p = Bukkit.getPlayer(uuid);
                String name = p != null ? p.getName() : "Unknown";
                activeSessions.put(uuid, new ControlledSession(uuid, name, "System", null));
            } catch (Exception ignored) {
            }
        }
    }

    public void saveSessions() {
        List<String> list = new ArrayList<>();
        for (UUID u : activeSessions.keySet()) {
            list.add(u.toString());
        }
        cfg().set("controlled", list);
        plugin.saveConfig();
    }

    public boolean isControlled(UUID uuid) {
        return activeSessions.containsKey(uuid);
    }

    public ControlledSession getSession(UUID uuid) {
        return activeSessions.get(uuid);
    }

    public Map<UUID, ControlledSession> getActiveSessions() {
        return activeSessions;
    }

    public boolean hasControlLocation() {
        return cfg().isSet("location.world") && Bukkit.getWorld(cfg().getString("location.world")) != null;
    }

    public Location getControlLocation() {
        if (!hasControlLocation()) return null;
        World world = Bukkit.getWorld(cfg().getString("location.world"));
        return new Location(
                world,
                cfg().getDouble("location.x"),
                cfg().getDouble("location.y"),
                cfg().getDouble("location.z"),
                (float) cfg().getDouble("location.yaw"),
                (float) cfg().getDouble("location.pitch")
        );
    }

    public void setControlLocation(Location loc) {
        cfg().set("location.world", loc.getWorld().getName());
        cfg().set("location.x", loc.getX());
        cfg().set("location.y", loc.getY());
        cfg().set("location.z", loc.getZ());
        cfg().set("location.yaw", (double) loc.getYaw());
        cfg().set("location.pitch", (double) loc.getPitch());
        plugin.saveConfig();
    }

    public boolean startControl(Player target, CommandSender staff) {
        if (isControlled(target.getUniqueId())) return false;
        Location controlLoc = getControlLocation();
        if (controlLoc == null) return false;

        ControlledSession session = new ControlledSession(
                target.getUniqueId(),
                target.getName(),
                staff.getName(),
                target.getLocation().clone()
        );

        // Create BossBar
        if (cfg().getBoolean("bossbar.enabled", true)) {
            String title = lang().getRaw("bossbar.title", "&c&lUNDER INSPECTION");
            BarColor color = BarColor.valueOf(cfg().getString("bossbar.color", "RED").toUpperCase());
            BarStyle style = BarStyle.valueOf(cfg().getString("bossbar.style", "SOLID").toUpperCase());
            BossBar bar = Bukkit.createBossBar(title, color, style);
            bar.addPlayer(target);
            if (staff instanceof Player) {
                bar.addPlayer((Player) staff);
            }
            session.setBossBar(bar);
        }

        activeSessions.put(target.getUniqueId(), session);
        saveSessions();

        // Teleport
        target.teleport(controlLoc);

        // Apply visuals and notification messages
        applyControlVisuals(target);

        // Play sound
        playSound(target, "sounds.control-start");

        // Staff title & subtitle
        if (staff instanceof Player) {
            Player staffPlayer = (Player) staff;
            String sTitle = lang().getRaw("staff-notifications.start-title.title", "&#B499DD&lINSPECTION STARTED")
                    .replace("%player%", target.getName());
            String sSubtitle = lang().getRaw("staff-notifications.start-title.subtitle", "&#B499DD%player% &fhas been taken into inspection.")
                    .replace("%player%", target.getName());
            int fadeIn = lang().getInt("staff-notifications.start-title.fade-in", 10);
            int stay = lang().getInt("staff-notifications.start-title.stay", 60);
            int fadeOut = lang().getInt("staff-notifications.start-title.fade-out", 20);
            staffPlayer.sendTitle(sTitle, sSubtitle, fadeIn, stay, fadeOut);
            playSound(staffPlayer, "sounds.staff-notify");
        }

        return true;
    }

    public boolean endControl(Player target, CommandSender staff) {
        ControlledSession session = activeSessions.remove(target.getUniqueId());
        if (session == null) return false;

        saveSessions();

        // Remove BossBar
        if (session.getBossBar() != null) {
            session.getBossBar().removeAll();
        }

        // Remove Blindness
        target.removePotionEffect(PotionEffectType.BLINDNESS);

        // Teleport back to original location if enabled
        if (cfg().getBoolean("teleport-back-on-finish", true) && session.getOriginalLocation() != null) {
            target.teleport(session.getOriginalLocation());
        }

        // Player Title and Subtitle
        String fTitle = lang().getRaw("player-notifications.finish-title.title", "&#B499DD&lINSPECTION FINISHED");
        String fSub = lang().getRaw("player-notifications.finish-title.subtitle", "&fInspection completed, enjoy the game!");
        int fadeIn = lang().getInt("player-notifications.finish-title.fade-in", 10);
        int stay = lang().getInt("player-notifications.finish-title.stay", 60);
        int fadeOut = lang().getInt("player-notifications.finish-title.fade-out", 20);
        target.sendTitle(fTitle, fSub, fadeIn, stay, fadeOut);

        String finishMsg = lang().getRaw("player-notifications.finish-message", "&aYour inspection has finished. Have a great game!");
        if (!finishMsg.isEmpty()) {
            target.sendMessage(finishMsg);
        }

        playSound(target, "sounds.control-end");

        // Staff Title and Subtitle
        if (staff instanceof Player) {
            Player staffPlayer = (Player) staff;
            String sTitle = lang().getRaw("staff-notifications.finish-title.title", "&#B499DD&lINSPECTION FINISHED")
                    .replace("%player%", target.getName());
            String sSubtitle = lang().getRaw("staff-notifications.finish-title.subtitle", "&#B499DD%player%'s &finspection has ended.")
                    .replace("%player%", target.getName());
            int sFadeIn = lang().getInt("staff-notifications.finish-title.fade-in", 10);
            int sStay = lang().getInt("staff-notifications.finish-title.stay", 60);
            int sFadeOut = lang().getInt("staff-notifications.finish-title.fade-out", 20);
            staffPlayer.sendTitle(sTitle, sSubtitle, sFadeIn, sStay, sFadeOut);
            playSound(staffPlayer, "sounds.staff-notify");
        }

        return true;
    }

    public void applyControlVisuals(Player target) {
        if (cfg().getBoolean("effects.blindness.enabled", true)) {
            int amplifier = cfg().getInt("effects.blindness.amplifier", 1);
            target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, amplifier, false, false, false));
        }

        String title = lang().getRaw("player-notifications.control-title.title", "&#B499DD&lFROZEN");
        String subtitle = lang().getRaw("player-notifications.control-title.subtitle", "&fYou have been frozen for suspected cheating");
        int fadeIn = lang().getInt("player-notifications.control-title.fade-in", 10);
        int stay = lang().getInt("player-notifications.control-title.stay", 100);
        int fadeOut = lang().getInt("player-notifications.control-title.fade-out", 20);
        target.sendTitle(title, subtitle, fadeIn, stay, fadeOut);

        List<String> messages = lang().getStringList("player-notifications.control-messages");
        for (String line : messages) {
            target.sendMessage(line);
        }
    }

    public void handleQuit(Player player) {
        if (!isControlled(player.getUniqueId())) return;

        ControlledSession session = activeSessions.get(player.getUniqueId());
        if (session != null && session.getBossBar() != null) {
            session.getBossBar().removeAll();
        }

        // Notify staff
        String alert = lang().getRaw("staff-notifications.player-quit-alert", "&4&l[WARNING] &cPlayer &f%player% &cleft the game during inspection!")
                .replace("%player%", player.getName());
        notifyStaff(alert);

        // Execute automatic punishment commands
        if (cfg().getBoolean("punishments.quit-punishment.enabled", false)) {
            List<String> commands = cfg().getStringList("punishments.quit-punishment.commands");
            for (String cmd : commands) {
                String parsed = cmd.replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsed);
            }
        }
    }

    public void handleConfession(Player player) {
        if (!isControlled(player.getUniqueId())) {
            player.sendMessage(plugin.getMessage("not-in-control"));
            return;
        }

        String msg = lang().getRaw("confession.player-message", "&eYour confession has been recorded. Staff will consider penalty reduction.");
        player.sendMessage(msg);

        String staffAlert = lang().getRaw("confession.staff-alert", "&#B499DD[CONFESSION] &f%player% &ehas admitted to cheating!")
                .replace("%player%", player.getName());
        notifyStaff(staffAlert);

        if (cfg().getBoolean("confession.auto-punish", false)) {
            List<String> cmds = cfg().getStringList("confession.punish-commands");
            for (String cmd : cmds) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
            }
        }
    }

    public void notifyStaff(String message) {
        String prefix = plugin.getPrefix();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (plugin.hasStaffPermission(p)) {
                p.sendMessage(prefix + message);
            }
        }
    }

    public void playSound(Player player, String configPath) {
        if (player == null) return;
        try {
            if (cfg().getBoolean(configPath + ".enabled", true)) {
                String soundName = cfg().getString(configPath + ".sound", "BLOCK_NOTE_BLOCK_PLING");
                float volume = (float) cfg().getDouble(configPath + ".volume", 1.0);
                float pitch = (float) cfg().getDouble(configPath + ".pitch", 1.0);
                player.playSound(player.getLocation(), Sound.valueOf(soundName.toUpperCase()), volume, pitch);
            }
        } catch (Exception ignored) {
        }
    }

    private void startTicker() {
        // Ticker task updating ActionBar and BossBar duration every second
        tickerTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Map.Entry<UUID, ControlledSession> entry : activeSessions.entrySet()) {
                Player p = Bukkit.getPlayer(entry.getKey());
                if (p == null || !p.isOnline()) continue;

                ControlledSession session = entry.getValue();
                long sec = session.getDurationSeconds();
                long minutes = sec / 60;
                long seconds = sec % 60;
                String timeStr = String.format("%02d:%02d", minutes, seconds);

                // Update BossBar
                if (session.getBossBar() != null) {
                    String baseTitle = lang().getRaw("bossbar.title", "&c&lUNDER INSPECTION");
                    String timeFormat = lang().getRaw("bossbar.time-format", " &7[%time%]").replace("%time%", timeStr);
                    session.getBossBar().setTitle(baseTitle + " " + timeFormat);
                }

                // Send ActionBar
                if (cfg().getBoolean("actionbar.enabled", true)) {
                    String barText = lang().getRaw("actionbar.message", "&eTime: &f%time% &8| &cContact staff on Discord!")
                            .replace("%time%", timeStr)
                            .replace("%player%", p.getName());
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(barText));
                }
            }
        }, 20L, 20L);
    }

    public void cleanup() {
        if (tickerTask != null) {
            tickerTask.cancel();
        }
        for (ControlledSession s : activeSessions.values()) {
            if (s.getBossBar() != null) {
                s.getBossBar().removeAll();
            }
        }
    }
}
