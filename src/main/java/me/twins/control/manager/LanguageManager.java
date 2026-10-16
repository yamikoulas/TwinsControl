package me.twins.control.manager;

import me.twins.control.TwinsControl;
import me.twins.control.utils.ColorUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LanguageManager {

    private final TwinsControl plugin;
    private FileConfiguration langConfig;
    private File langFile;
    private String currentLanguage = "en";

    public LanguageManager(TwinsControl plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        File langDir = new File(plugin.getDataFolder(), "lang");
        if (!langDir.exists()) {
            langDir.mkdirs();
        }

        saveResourceIfNotExists("lang/messages_en.yml");
        saveResourceIfNotExists("lang/messages.en.yml");
        saveResourceIfNotExists("lang/messages_tr.yml");

        this.currentLanguage = plugin.getConfig().getString("language", "en").toLowerCase();

        // Check potential file locations in lang folder
        File targetFile = new File(langDir, "messages_" + currentLanguage + ".yml");
        if (!targetFile.exists()) {
            targetFile = new File(langDir, "messages." + currentLanguage + ".yml");
        }
        if (!targetFile.exists()) {
            targetFile = new File(langDir, currentLanguage + ".yml");
        }
        if (!targetFile.exists()) {
            targetFile = new File(langDir, "messages_en.yml");
        }

        this.langFile = targetFile;
        this.langConfig = YamlConfiguration.loadConfiguration(this.langFile);

        // Load default from JAR for fallback values
        InputStream defaultStream = plugin.getResource("lang/messages_en.yml");
        if (defaultStream == null) {
            defaultStream = plugin.getResource("lang/messages.en.yml");
        }
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream, StandardCharsets.UTF_8));
            this.langConfig.setDefaults(defaultConfig);
        }
    }

    public void reload() {
        load();
    }

    private void saveResourceIfNotExists(String resourcePath) {
        File file = new File(plugin.getDataFolder(), resourcePath);
        if (!file.exists()) {
            try {
                plugin.saveResource(resourcePath, false);
            } catch (Exception ignored) {
            }
        }
    }

    public String getPrefix() {
        String prefix = langConfig.getString("prefix");
        if (prefix == null || prefix.isEmpty()) {
            prefix = plugin.getConfig().getString("prefix", "<gradient:#B499DD:#A66AFF>ᴛᴡɪɴѕ ᴄᴏɴᴛʀᴏʟ</gradient> &8» ");
        }
        return ColorUtil.color(prefix);
    }

    public String getMessage(String path) {
        String msg = langConfig.getString("messages." + path);
        if (msg == null) {
            msg = plugin.getConfig().getString("messages." + path, "");
        }
        if (msg.isEmpty()) return "";
        return getPrefix() + ColorUtil.color(msg);
    }

    public String getRaw(String path, String defaultValue) {
        String val = langConfig.getString(path);
        if (val == null) {
            val = plugin.getConfig().getString(path, defaultValue);
        }
        return ColorUtil.color(val);
    }

    public int getInt(String path, int defaultValue) {
        if (langConfig.contains(path)) {
            return langConfig.getInt(path);
        }
        return plugin.getConfig().getInt(path, defaultValue);
    }

    public List<String> getStringList(String path) {
        List<String> list = langConfig.getStringList(path);
        if (list == null || list.isEmpty()) {
            list = plugin.getConfig().getStringList(path);
        }
        return ColorUtil.color(list);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public FileConfiguration getLangConfig() {
        return langConfig;
    }
}
