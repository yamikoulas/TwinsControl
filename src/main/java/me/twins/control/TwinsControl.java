package me.twins.control;

import me.twins.control.commands.ConfessCommand;
import me.twins.control.commands.ControlCommand;
import me.twins.control.commands.ControlTabCompleter;
import me.twins.control.commands.FinishControlCommand;
import me.twins.control.commands.SetControlCommand;
import me.twins.control.listeners.PlayerConnectionListener;
import me.twins.control.listeners.PlayerControlListener;
import me.twins.control.manager.ControlManager;
import me.twins.control.manager.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class TwinsControl extends JavaPlugin {

    private static TwinsControl instance;
    private LanguageManager languageManager;
    private ControlManager controlManager;

    public static TwinsControl getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // Initialize Language Manager
        this.languageManager = new LanguageManager(this);

        // Initialize Control Manager
        this.controlManager = new ControlManager(this);

        // Register Event Listeners
        Bukkit.getPluginManager().registerEvents(new PlayerControlListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(this), this);

        // Register Commands
        if (getCommand("control") != null) {
            getCommand("control").setExecutor(new ControlCommand(this));
            getCommand("control").setTabCompleter(new ControlTabCompleter(this));
        }

        if (getCommand("finishcontrol") != null) {
            getCommand("finishcontrol").setExecutor(new FinishControlCommand(this));
        }

        if (getCommand("setcontrol") != null) {
            getCommand("setcontrol").setExecutor(new SetControlCommand(this));
        }

        if (getCommand("confess") != null) {
            getCommand("confess").setExecutor(new ConfessCommand(this));
        }

        getLogger().info("TwinsControl has been successfully enabled! (Language: " + languageManager.getCurrentLanguage() + ")");
    }

    @Override
    public void onDisable() {
        if (controlManager != null) {
            controlManager.saveSessions();
            controlManager.cleanup();
        }
        getLogger().info("TwinsControl has been disabled!");
    }

    public void reloadPlugin() {
        reloadConfig();
        if (languageManager != null) {
            languageManager.reload();
        }
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public ControlManager getControlManager() {
        return controlManager;
    }

    public String getPrefix() {
        if (languageManager != null) {
            return languageManager.getPrefix();
        }
        return getConfig().getString("prefix", "<gradient:#B499DD:#A66AFF>ᴛᴡɪɴѕ ᴄᴏɴᴛʀᴏʟ</gradient> &8» ");
    }

    public String getMessage(String path) {
        if (languageManager != null) {
            return languageManager.getMessage(path);
        }
        return getConfig().getString("messages." + path, "");
    }

    public boolean hasStaffPermission(CommandSender sender) {
        return sender.hasPermission("twinscontrol.staff") || sender.hasPermission("twinscontrol.yetkili");
    }
}
