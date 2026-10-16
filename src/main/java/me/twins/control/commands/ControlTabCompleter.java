package me.twins.control.commands;

import me.twins.control.TwinsControl;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ControlTabCompleter implements TabCompleter {

    private final TwinsControl plugin;

    public ControlTabCompleter(TwinsControl plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (!plugin.hasStaffPermission(sender)) return list;

        if (args.length == 1) {
            String current = args[0].toLowerCase();
            if ("reload".startsWith(current)) list.add("reload");
            if ("list".startsWith(current)) list.add("list");
            if ("finish".startsWith(current)) list.add("finish");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(current)) {
                    list.add(p.getName());
                }
            }
        } else if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (sub.equals("finish") || sub.equals("bitir") || sub.equals("end")) {
                String current = args[1].toLowerCase();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (plugin.getControlManager().isControlled(p.getUniqueId()) && p.getName().toLowerCase().startsWith(current)) {
                        list.add(p.getName());
                    }
                }
            }
        }

        return list;
    }
}
