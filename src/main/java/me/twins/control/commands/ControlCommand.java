package me.twins.control.commands;

import me.twins.control.TwinsControl;
import me.twins.control.manager.ControlManager;
import me.twins.control.manager.LanguageManager;
import me.twins.control.model.ControlledSession;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class ControlCommand implements CommandExecutor {

    private final TwinsControl plugin;

    public ControlCommand(TwinsControl plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.hasStaffPermission(sender)) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        LanguageManager lang = plugin.getLanguageManager();

        if (args.length < 1) {
            sender.sendMessage(lang.getRaw("messages.help-header", "&8&m----------------------------------------"));
            sender.sendMessage(lang.getRaw("messages.help-title", "&b&lTwinsControl &7- &fCommand Guide:"));
            sender.sendMessage(lang.getRaw("messages.help-control", "&e/control <player> &7- Takes the player into inspection."));
            sender.sendMessage(lang.getRaw("messages.help-finishcontrol", "&e/finishcontrol <player> &7- Finishes the player's inspection."));
            sender.sendMessage(lang.getRaw("messages.help-list", "&e/control list &7- Lists all players currently under inspection."));
            sender.sendMessage(lang.getRaw("messages.help-reload", "&e/control reload &7- Reloads configuration and language."));
            sender.sendMessage(lang.getRaw("messages.help-setcontrol", "&e/setcontrol &7- Sets the inspection room location."));
            sender.sendMessage(lang.getRaw("messages.help-footer", "&8&m----------------------------------------"));
            return true;
        }

        String sub = args[0].toLowerCase();

        // Reload
        if (sub.equals("reload")) {
            plugin.reloadPlugin();
            sender.sendMessage(plugin.getMessage("reload-success"));
            return true;
        }

        ControlManager cm = plugin.getControlManager();

        // List
        if (sub.equals("list") || sub.equals("liste")) {
            Map<UUID, ControlledSession> sessions = cm.getActiveSessions();
            if (sessions.isEmpty()) {
                sender.sendMessage(plugin.getMessage("list-empty"));
                return true;
            }

            sender.sendMessage(lang.getRaw("messages.help-header", "&8&m----------------------------------------"));
            sender.sendMessage(lang.getRaw("messages.list-header", "&6&lActive Inspected Players &7(%count%):")
                    .replace("%count%", String.valueOf(sessions.size())));

            for (ControlledSession session : sessions.values()) {
                long sec = session.getDurationSeconds();
                long m = sec / 60;
                long s = sec % 60;
                String timeStr = String.format("%02d:%02d", m, s);

                sender.sendMessage(lang.getRaw("messages.list-entry", "&8- &f%player% &7| Time: &e%time% &7| Staff: &b%staff%")
                        .replace("%player%", session.getPlayerName())
                        .replace("%time%", timeStr)
                        .replace("%staff%", session.getStaffName()));
            }
            sender.sendMessage(lang.getRaw("messages.help-footer", "&8&m----------------------------------------"));
            return true;
        }

        // Shortcut: /control finish <player>
        if ((sub.equals("finish") || sub.equals("bitir") || sub.equals("end")) && args.length >= 2) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage(plugin.getMessage("player-not-found"));
                return true;
            }
            if (!cm.isControlled(target.getUniqueId())) {
                sender.sendMessage(plugin.getMessage("not-controlled"));
                return true;
            }
            cm.endControl(target, sender);
            sender.sendMessage(plugin.getMessage("control-finished-staff").replace("%player%", target.getName()));
            return true;
        }

        // Take player into inspection: /control <player>
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(plugin.getMessage("player-not-found"));
            return true;
        }

        if (cm.isControlled(target.getUniqueId())) {
            sender.sendMessage(plugin.getMessage("already-controlled"));
            return true;
        }

        if (!cm.hasControlLocation()) {
            sender.sendMessage(plugin.getMessage("location-not-set"));
            return true;
        }

        boolean success = cm.startControl(target, sender);
        if (success) {
            sender.sendMessage(plugin.getMessage("control-started-staff").replace("%player%", target.getName()));
        } else {
            sender.sendMessage(plugin.getMessage("world-not-found"));
        }

        return true;
    }
}
