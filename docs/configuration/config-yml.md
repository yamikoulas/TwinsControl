# config.yml

The main configuration file controls plugin mechanics, automation, visuals, and sound effects.

---

## Configuration Breakdown

```yaml
# =========================================================
#                 TwinsControl Configuration
# =========================================================

# Active language (options: "en", "tr" or custom "lang/messages_<lang>.yml")
language: "en"

# Should the player be teleported back to their original position when inspection finishes?
teleport-back-on-finish: true

# Potion Effects applied to the player during inspection
effects:
  blindness:
    enabled: true
    amplifier: 1 # Amplifier level for blindness

# Live BossBar Settings (Text is configured in lang/messages_<lang>.yml)
bossbar:
  enabled: true
  color: RED # RED, BLUE, GREEN, PINK, PURPLE, WHITE, YELLOW
  style: SOLID # SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20

# Live ActionBar Notification (Text is configured in lang/messages_<lang>.yml)
actionbar:
  enabled: true

# Sound Effects (Valid Spigot Sound enum names)
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
  auto-punish: false # Automatically execute commands below when a player confesses?
  punish-commands:
    - "tempban %player% 3d Admitted Cheating (Reduced)"

# Punishment for Leaving During Inspection
punishments:
  quit-punishment:
    enabled: false # Set to true to punish players quitting during inspection
    commands:
      - "ban %player% Leaving during cheat inspection"

# Saved control room location (configured via /setcontrol)
location: {}

# Actively controlled player UUIDs (managed automatically by the plugin)
controlled: []
```
