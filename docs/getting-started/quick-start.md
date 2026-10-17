# Quick Start Guide

Set up and execute your first player inspection in less than two minutes.

---

## Step 1: Set the Inspection Room

Before freezing any players, you need to designate an inspection room location.

1. Build or navigate to an isolated room (such as a glass cage or moderation hub).
2. Stand on the exact spot facing the direction you want suspects to look.
3. Run the command:
   ```text
   /setcontrol
   ```
4. You will see a confirmation message indicating the control location has been saved in `config.yml`.

---

## Step 2: Grant Staff Permissions

Grant your moderators or administrators the required permission node:

```text
twinscontrol.staff
```

If using LuckPerms:
```text
/lp group moderator permission set twinscontrol.staff true
```

---

## Step 3: Start an Inspection

When you suspect a player of using disallowed modifications (hacks/cheats):

1. Run:
   ```text
   /control <player>
   ```
2. The suspect is immediately:
   * Teleported to the inspection room location.
   * Frozen in place with blindness applied.
   * Presented with on-screen titles, chat warnings, BossBar, and ActionBar timers.
3. Their pre-inspection coordinates and inventory state are securely recorded.

---

## Step 4: Complete the Inspection

Once the inspection or screenshare has concluded:

1. Run:
   ```text
   /finishcontrol <player>
   ```
2. The player is notified, all freeze restrictions and status effects are removed, and they are safely teleported back to their original coordinates.
