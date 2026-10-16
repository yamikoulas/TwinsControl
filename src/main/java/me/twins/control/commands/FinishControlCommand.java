package me.twins.control.commands;

import me.twins.control.TwinsControl;
import me.twins.control.manager.ControlManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FinishControlCommand implements CommandExecutor {

    private final TwinsControl plugin;

    public FinishControlCommand(TwinsControl plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.hasStaffPermission(sender)) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.getMessage("usage-finishcontrol"));
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(plugin.getMessage("player-not-found"));
            return true;
        }

        ControlManager cm = plugin.getControlManager();
        if (!cm.isControlled(target.getUniqueId())) {
            sender.sendMessage(plugin.getMessage("not-controlled"));
            return true;
        }

        boolean success = cm.endControl(target, sender);
        if (success) {
            sender.sendMessage(plugin.getMessage("control-finished-staff").replace("%player%", target.getName()));
        }

        return true;
    }
}
