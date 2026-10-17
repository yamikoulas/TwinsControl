# Combat-Log & Quit Punishment

A common issue during screenshare checks is players disconnecting abruptly to avoid detection or delete files. TwinsControl ensures that quitting under inspection never goes unpunished.

---

## Disconnect Detection

When a player under active inspection leaves the game:

1. **Staff Alert:**
   An instant warning is broadcasted to all online staff with permission:
   ```text
   [WARNING] PlayerName left the game while under inspection!
   ```

2. **Session Cleanup:**
   The player's active timer, BossBar, and temporary states are cleared cleanly from memory without leaving phantom data.

3. **Automatic Punishments:**
   If enabled in `config.yml`, TwinsControl executes console commands immediately upon disconnection.

---

## Configuration

Enable quit punishment in `config.yml`:

```yaml
punishments:
  quit-punishment:
    enabled: true
    commands:
      - "ban %player% Evading cheat inspection / Disconnecting during screenshare"
```

You can specify any command supported by your server or punishment management plugin (such as LiteBans, AdvancedBan, or Vanilla Ban).
