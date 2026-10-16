package me.twins.control.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColorUtilTest {

    @Test
    public void testPrefixGradientAndLegacy() {
        String input = "<gradient:#B499DD:#A66AFF>ᴛᴡɪɴѕ ᴄᴏɴᴛʀᴏʟ</gradient> &8» ";
        String colored = ColorUtil.color(input);
        assertNotNull(colored);
        // Should contain section symbol §
        assertTrue(colored.contains("§x§b§4§9§9§d§d") || colored.contains("§x§B§4§9§9§D§D"), "Should contain hex gradient colors");
        assertTrue(colored.contains("§8» "), "Should translate &8 into §8");
    }

    @Test
    public void testHexColor() {
        String input = "&#B499DD&lᴅᴏɴᴅᴜʀᴜʟᴅᴜɴ";
        String colored = ColorUtil.color(input);
        assertNotNull(colored);
        assertTrue(colored.contains("§l"), "Should contain bold code §l");
        assertTrue(colored.contains("§x§b§4§9§9§d§d") || colored.contains("§x§B§4§9§9§D§D"), "Should parse hex color");
    }

    @Test
    public void testDoubleAmpersandHex() {
        String input = "&&#B499DDPlayer";
        String colored = ColorUtil.color(input);
        assertNotNull(colored);
        assertFalse(colored.startsWith("&"), "Should not have leading dangling &");
        assertTrue(colored.contains("§x§b§4§9§9§d§d") || colored.contains("§x§B§4§9§9§D§D"));
    }

    @Test
    public void testBracketHex() {
        String input = "<#B499DD>Player</#B499DD>";
        String colored = ColorUtil.color(input);
        assertNotNull(colored);
        assertTrue(colored.contains("§x§b§4§9§9§d§d") || colored.contains("§x§B§4§9§9§D§D"));
    }

    @Test
    public void testLegacyColors() {
        String input = "&cHello &eWorld";
        String colored = ColorUtil.color(input);
        assertEquals("§cHello §eWorld", colored);
    }
}
