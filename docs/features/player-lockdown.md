# Player Lockdown

TwinsControl enforces an airtight sandbox environment around suspected players to prevent any griefing, item disposal, or combat evasion.

---

## Prevented Actions

When a player is placed under inspection, TwinsControl automatically intercepts and cancels the following actions:

* **Movement:** Players can rotate their head to look around, but their X, Y, and Z position is locked.
* **Block Interaction:** Players cannot break, place, hit, or interact with any blocks (including chests, levers, buttons, or doors).
* **Inventory Management:** Opening chests, opening workbenches, or moving items inside their personal inventory is disabled.
* **Item Dropping & Pickup:** Dropping items (to hide cheat evidence or transfer gear) and picking up items from the floor is strictly blocked.
* **Damage Immunity:** 
  * Inspected players cannot deal damage to players or mobs.
  * Inspected players cannot take any form of damage (fall damage, suffocation, PvP, fire, etc.), guaranteeing they do not die while frozen.
* **Command Blocking:** All commands are cancelled to prevent teleportation exploits or unauthorized communication. The only permitted command is `/confess` (or `/itiraf`).

---

## Visual & Potion Effects

* **Blindness:** By default, inspected players receive a configurable blindness effect to limit distractions and keep focus on the screenshare.
* **Immersion Sounds:** Audio cues trigger when an inspection begins and when it ends to alert the player immediately.
