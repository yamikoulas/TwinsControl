package me.twins.control.commands;

import me.twins.control.TwinsControl;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetControlCommand implements CommandExecutor {

    private final TwinsControl plugin;

    public SetControlCommand(TwinsControl plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!plugin.hasStaffPermission(sender)) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();
        plugin.getControlManager().setControlLocation(loc);
        player.sendMessage(plugin.getMessage("location-set-success"));
        plugin.getControlManager().playSound(player, "sounds.staff-notify");
        return true;
    }
}
