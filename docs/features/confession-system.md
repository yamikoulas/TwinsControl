# Confession System

To save time for both staff members and players, TwinsControl features an integrated confession mechanic.

---

## How It Works

1. During an inspection, players are notified that they can run:
   ```text
   /confess
   ```
   *(or `/itiraf` in Turkish)*

2. When the command is executed:
   * A notification sound (`BLOCK_NOTE_BLOCK_BELL`) plays for online staff.
   * A staff broadcast announces that the player has admitted to cheating.
   * The player receives confirmation that their confession has been logged.

---

## Automatic Punishment (Optional)

If your server offers a standardized reduced sentence policy (e.g., a 3-day tempban instead of a 30-day ban for admitting guilt), TwinsControl can execute punishment commands automatically.

In `config.yml`:

```yaml
confession:
  auto-punish: true
  punish-commands:
    - "tempban %player% 3d Admitted Cheating (Reduced Sentence)"
    - "finishcontrol %player%"
```

When `auto-punish` is set to `false`, staff can handle the punishment manually according to their internal guidelines.
