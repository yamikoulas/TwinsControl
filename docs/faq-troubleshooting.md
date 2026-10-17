# FAQ & Troubleshooting

Answers to common questions and solutions for troubleshooting TwinsControl.

---

## Frequently Asked Questions

### Does TwinsControl require any external dependencies or libraries?
No. TwinsControl is completely self-contained. Adventure libraries are relocated and shaded inside the plugin JAR, so you do not need ProtocolLib or any other dependency.

### Can players be hurt or die while under inspection?
No. TwinsControl automatically cancels all incoming damage sources (PvP, fall, fire, drowning, suffocation) for any player while their inspection is active.

### Does TwinsControl remember where players were before being frozen?
Yes. Pre-control coordinates are saved. When `/finishcontrol <player>` is run, the player is safely teleported back to their starting point (unless `teleport-back-on-finish` is set to `false`).

---

## Troubleshooting

### "You must set a control location first using /setcontrol!"
**Cause:** You attempted to run `/control <player>` before defining the inspection spawn point.  
**Solution:** Stand where you want the suspect to appear and run `/setcontrol`.

### "Saved control world could not be found!"
**Cause:** The world where `/setcontrol` was initially run was renamed or deleted.  
**Solution:** Navigate to your current inspection room and run `/setcontrol` again to update the location.

### Sounds fail to play or throw console warnings
**Cause:** Sound enum names vary between major Minecraft versions (e.g., 1.16 vs 1.21).  
**Solution:** Verify that the sound name in `config.yml` exists in your server version's `org.bukkit.Sound` enum list, or update it to a universal sound.
