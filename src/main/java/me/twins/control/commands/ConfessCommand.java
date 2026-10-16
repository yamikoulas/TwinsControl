package me.twins.control.commands;

import me.twins.control.TwinsControl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfessCommand implements CommandExecutor {

    private final TwinsControl plugin;

    public ConfessCommand(TwinsControl plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;
        plugin.getControlManager().handleConfession(player);
        return true;
    }
}
