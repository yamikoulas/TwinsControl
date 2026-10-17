# Commands & Permissions

Comprehensive list of all commands, aliases, arguments, and permission nodes provided by TwinsControl.

---

## Permissions Overview

| Permission Node | Description | Default |
| :--- | :--- | :--- |
| `twinscontrol.staff` | Grants access to all staff moderation commands | OP |
| `twinscontrol.yetkili` | Legacy alias for `twinscontrol.staff` | OP |

---

## Command Reference

### `/control <player>`
* **Aliases:** `/kontrol`, `/tc`, `/twinscontrol`
* **Permission:** `twinscontrol.staff`
* **Description:** Freezes the targeted player, teleports them to the configured inspection room, and starts the session timer.

### `/finishcontrol <player>`
* **Aliases:** `/kontrolbitir`, `/endcontrol`
* **Permission:** `twinscontrol.staff`
* **Description:** Concludes the inspection session, removes all freeze constraints, and safely teleports the player back to their original position (if enabled).

### `/control list`
* **Aliases:** `/kontrol list`, `/kontrol liste`
* **Permission:** `twinscontrol.staff`
* **Description:** Displays a list of all players currently under inspection, the staff member who initiated it, and elapsed times.

### `/setcontrol`
* **Aliases:** `/setkontrol`
* **Permission:** `twinscontrol.staff`
* **Description:** Sets the inspection spawn point to your current coordinates (X, Y, Z, Yaw, Pitch, World).

### `/control reload`
* **Aliases:** `/kontrol reload`
* **Permission:** `twinscontrol.staff`
* **Description:** Reloads `config.yml` and the active language file without needing a server restart.

### `/confess`
* **Aliases:** `/itiraf`
* **Permission:** None (Available to any player currently under inspection)
* **Description:** Allows a frozen player to admit to cheating, notifying staff and optionally triggering reduced punishment commands.
