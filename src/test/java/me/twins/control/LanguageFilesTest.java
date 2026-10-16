package me.twins.control;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LanguageFilesTest {

    @Test
    public void testLanguageFilesValidityAndKeys() {
        InputStream enStream = getClass().getClassLoader().getResourceAsStream("lang/messages_en.yml");
        assertNotNull(enStream, "lang/messages_en.yml must exist in resources");

        YamlConfiguration enConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(enStream, StandardCharsets.UTF_8));
        assertNotNull(enConfig.getString("prefix"));
        assertEquals("&c&lUNDER INSPECTION", enConfig.getString("bossbar.title"));
        assertNotNull(enConfig.getString("messages.no-permission"));
        assertNotNull(enConfig.getString("messages.help-title"));

        InputStream dotEnStream = getClass().getClassLoader().getResourceAsStream("lang/messages.en.yml");
        assertNotNull(dotEnStream, "lang/messages.en.yml must exist in resources");

        InputStream trStream = getClass().getClassLoader().getResourceAsStream("lang/messages_tr.yml");
        assertNotNull(trStream, "lang/messages_tr.yml must exist in resources");

        YamlConfiguration trConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(trStream, StandardCharsets.UTF_8));
        assertNotNull(trConfig.getString("prefix"));
        assertEquals("&c&lKONTROL EDİLİYORSUNUZ", trConfig.getString("bossbar.title"));
        assertNotNull(trConfig.getString("messages.no-permission"));

        // Verify key matching for messages section
        Set<String> enKeys = enConfig.getConfigurationSection("messages").getKeys(true);
        Set<String> trKeys = trConfig.getConfigurationSection("messages").getKeys(true);

        for (String key : enKeys) {
            assertTrue(trKeys.contains(key), "messages_tr.yml is missing key: messages." + key);
        }
    }

    @Test
    public void testConfigFileEnglishDefault() {
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.yml");
        assertNotNull(configStream, "config.yml must exist in resources");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, StandardCharsets.UTF_8));
        assertEquals("en", config.getString("language"), "Default language should be 'en'");
        assertTrue(config.getBoolean("teleport-back-on-finish"));
        assertTrue(config.getBoolean("effects.blindness.enabled"));
    }
}
