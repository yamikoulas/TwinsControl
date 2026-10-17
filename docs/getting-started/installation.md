# Installation

This guide walks you through installing TwinsControl on your Minecraft server.

---

## Requirements

Before installing TwinsControl, ensure your environment meets the following specifications:

* **Server Software:** Paper, Purpur, Spigot, or Pufferfish
* **Minecraft Version:** `1.16.5` – `1.21.x+`
* **Java Version:** Java 17 or higher recommended (runtime supports Java 8 through 21)

---

## Step-by-Step Installation

1. **Download the Plugin:**
   Download the latest release JAR file (`TwinsControl-x.x.x.jar`) from Modrinth, SpigotMC, or GitHub Releases.

2. **Place in Plugins Directory:**
   Drop the downloaded `.jar` file into your server's `plugins/` directory:
   ```text
   server/
   ├── plugins/
   │   └── TwinsControl-1.0.0.jar
   ```

3. **Start or Restart your Server:**
   Start your server or run a restart. TwinsControl will automatically generate default files in `plugins/TwinsControl/`:
   * `config.yml`
   * `lang/messages_en.yml`
   * `lang/messages_tr.yml`

4. **Verify Installation:**
   In your console or in-game (as an OP), run:
   ```text
   /control
   ```
   If TwinsControl is running correctly, you will see the help menu displayed in chat.
