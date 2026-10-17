# Live Timers & Display

Keeping both the moderation team and the suspected player informed about the active session time is crucial during screenshare procedures.

---

## Live BossBar

TwinsControl creates a dedicated BossBar for every player placed under control:

* **Dynamic Progress:** Updates every second with the active duration (`MM:SS`).
* **Custom Color & Style:** Configure colors (`RED`, `BLUE`, `GREEN`, `PINK`, `PURPLE`, `WHITE`, `YELLOW`) and styles (`SOLID`, `SEGMENTED_6`, `SEGMENTED_10`, `SEGMENTED_12`, `SEGMENTED_20`) in `config.yml`.
* **Translatable Text:** The title text and time placeholder `%time%` can be customized in your active language file.

---

## Live ActionBar

In addition to the BossBar, an ActionBar ticker continuously broadcasts real-time status at the bottom of the player's screen:

* Shows elapsed inspection time.
* Displays instructions (e.g., advising the player to join your Discord server screenshare voice channel).
* Configurable via the `actionbar` section in `config.yml` and the language file.

---

## Titles & Chat Warnings

Upon freezing:
* An on-screen **Title and Subtitle** alert the player with custom fade-in, stay, and fade-out durations.
* A formatted multi-line chat warning outlines the rules, the risks of disconnecting, and the option to confess.
