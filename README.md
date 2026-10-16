# TwinsControl

<p align="center">
  <img src="https://img.shields.io/badge/Minecraft-1.16.5--1.21.x+-blue.svg" alt="Minecraft Version">
  <img src="https://img.shields.io/badge/Java-8%20to%2021-orange.svg" alt="Java Support">
  <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT">
  <img src="https://img.shields.io/badge/Language-Multi--Language-purple.svg" alt="Multi-Language">
</p>

**TwinsControl** is a modern, lightweight, and highly configurable cheat inspection (Screenshare / Freeze) plugin for Minecraft servers running on **Paper, Purpur, Spigot, and Pufferfish** from version **1.16.5 up to 1.21.x+**.

It enables server staff to securely freeze suspected cheaters, teleport them to a dedicated control room, monitor inspection duration via live BossBar and ActionBar timers, and manage confessions or disconnect punishments cleanly.

---

## Features

- **Full Player Lockdown:** 
  Frozen players can look around but cannot move, break or place blocks, drop or pick up items, open inventories, attack other entities, or take damage (complete invulnerability).
- **Live BossBar & ActionBar Counters:** 
  Real-time visual duration ticker showing exactly how long the inspection has lasted.
- **Confession System:** 
  Players can run `/confess` (or `/itiraf`) to admit cheating, notifying online staff and optionally triggering automatic reduced penalty commands.
- **Combat-Log & Quit Punishment:** 
  Automatically executes configurable ban/tempban commands if a player tries to disconnect during an active inspection.
- **Smart Return Teleportation:** 
  Automatically teleports players back to their exact original location once the inspection concludes.
- **Audio & Visual Effects:** 
  Built-in blindness effect, custom Title/Subtitle notifications with configurable fade/stay timings, and sound cues.
- **Multi-Language Support (`lang/` folder):** 
  Comes with built-in English (`lang/messages_en.yml`) and Turkish (`lang/messages_tr.yml`) language files.
- **HEX & Gradient Colors:** 
  Full support for MiniMessage tags, gradients, and HEX color codes (`&#RRGGBB`).
- **Zero NMS Dependencies:** 
  Built purely on Bukkit/Spigot API with shaded and relocated Adventure libraries, ensuring future-proof stability.

---

## Commands & Permissions

### Staff Commands (`twinscontrol.staff` or `twinscontrol.yetkili`)

| Command | Aliases | Description |
| :--- | :--- | :--- |
| `/control <player>` | `/kontrol`, `/tc`, `/twinscontrol` | Freezes and teleports a player to the control room |
| `/finishcontrol <player>` | `/kontrolbitir`, `/endcontrol` | Ends inspection and safely returns the player |
| `/control list` | `/kontrol list`, `/kontrol liste` | Displays all currently inspected players and timers |
| `/setcontrol` | `/setkontrol` | Sets the inspection room spawn at your current location |
| `/control reload` | `/kontrol reload` | Reloads configuration and active language files |

### Player Commands

| Command | Aliases | Description |
| :--- | :--- | :--- |
| `/confess` | `/itiraf` | Allows a frozen player to admit to cheating |

---

## Configuration

### `config.yml`

```yaml
# Active language file in lang/ directory (options: "en", "tr" or custom)
language: "en"

# Teleport player back to original position when inspection ends?
teleport-back-on-finish: true

# Potion effects applied during inspection
effects:
  blindness:
    enabled: true
    amplifier: 1

# Live BossBar Settings (Text configured in lang/messages_<lang>.yml)
bossbar:
  enabled: true
  color: RED
  style: SOLID

# Live ActionBar Notification
actionbar:
  enabled: true

# Sound Effects (Minecraft Sound Enum Names)
sounds:
  control-start:
    enabled: true
    sound: "ENTITY_WITHER_SPAWN"
    volume: 1.0
    pitch: 1.0
  control-end:
    enabled: true
    sound: "UI_TOAST_CHALLENGE_COMPLETE"
    volume: 1.0
    pitch: 1.0
  staff-notify:
    enabled: true
    sound: "BLOCK_NOTE_BLOCK_BELL"
    volume: 1.0
    pitch: 1.2

# Confession System Settings
confession:
  auto-punish: false
  punish-commands:
    - "tempban %player% 3d Admitted Cheating (Reduced)"

# Punishment for Leaving During Inspection
punishments:
  quit-punishment:
    enabled: false
    commands:
      - "ban %player% Leaving during cheat inspection"

location: {}
controlled: []
```

---

## Building from Source

To build TwinsControl yourself, you will need **Java 17+** and **Maven** installed:

```bash
git clone https://github.com/yamikoulas/TwinsControl.git
cd TwinsControl
mvn clean package
```

The compiled and shaded JAR file will be located in the `target/` directory:
```
target/TwinsControl-1.0.0.jar
```

---

## License

TwinsControl is open-source software licensed under the [MIT License](LICENSE).
