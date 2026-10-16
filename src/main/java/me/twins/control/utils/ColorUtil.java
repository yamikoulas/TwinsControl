package me.twins.control.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .character('§')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    // Supports &#RRGGBB, &&#RRGGBB
    private static final Pattern HEX_PATTERN = Pattern.compile("&?&#([A-Fa-f0-9]{6})");
    // Supports <#RRGGBB>
    private static final Pattern BRACKET_HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]{6})>");

    /**
     * Translates MiniMessage tags (<gradient:...>, <rainbow>, <color:...>, etc.),
     * Hex colors (&#RRGGBB, <#RRGGBB>), and legacy formatting codes (&a, &l, &r, etc.)
     * into Minecraft legacy formatted string with full RGB section codes (§x§r§r§g§g§b§b).
     *
     * @param text The input message text
     * @return Colored string ready to send to Bukkit/Spigot players
     */
    public static String color(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // 1. Process MiniMessage tags if tag brackets exist
        if (text.contains("<") && text.contains(">")) {
            try {
                Component component = MINI_MESSAGE.deserialize(text);
                text = LEGACY_SERIALIZER.serialize(component);
            } catch (Exception ignored) {
                // If MiniMessage fails (e.g. unclosed or non-tag brackets), gracefully continue
            }
        }

        // 2. Handle <#RRGGBB> format if any remain unparsed
        Matcher bracketMatcher = BRACKET_HEX_PATTERN.matcher(text);
        if (bracketMatcher.find()) {
            StringBuffer sb = new StringBuffer();
            do {
                String hex = bracketMatcher.group(1);
                bracketMatcher.appendReplacement(sb, ChatColor.of("#" + hex).toString());
            } while (bracketMatcher.find());
            bracketMatcher.appendTail(sb);
            text = sb.toString();
        }

        // 3. Handle &#RRGGBB or &&#RRGGBB hex patterns
        Matcher hexMatcher = HEX_PATTERN.matcher(text);
        if (hexMatcher.find()) {
            StringBuffer sb = new StringBuffer();
            do {
                String hex = hexMatcher.group(1);
                hexMatcher.appendReplacement(sb, ChatColor.of("#" + hex).toString());
            } while (hexMatcher.find());
            hexMatcher.appendTail(sb);
            text = sb.toString();
        }

        // 4. Translate classic alternate color codes (&a, &c, &l, etc.)
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Translates colors for a list of strings.
     *
     * @param list List of raw strings
     * @return List of colored strings
     */
    public static List<String> color(List<String> list) {
        if (list == null) return new ArrayList<>();
        List<String> colored = new ArrayList<>(list.size());
        for (String s : list) {
            colored.add(color(s));
        }
        return colored;
    }
}
