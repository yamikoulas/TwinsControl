# Localization & Colors

TwinsControl features an advanced multi-language engine with support for modern Minecraft text formatting.

---

## Language Files

Language files reside in the `plugins/TwinsControl/lang/` folder:

* `messages_en.yml` (English - Default)
* `messages_tr.yml` (Turkish)

To switch languages, change the `language` value in `config.yml` and run `/control reload`:

```yaml
language: "en"
```

To create a new language (e.g., German):
1. Copy `lang/messages_en.yml` to `lang/messages_de.yml`.
2. Translate the values.
3. Set `language: "de"` in `config.yml`.
4. Run `/control reload`.

---

## Color & Formatting Support

TwinsControl supports three text formatting styles seamlessly:

### 1. MiniMessage & Gradients
Apply smooth color gradients and tags:
```yaml
prefix: "<gradient:#B499DD:#A66AFF>ᴛᴡɪɴѕ ᴄᴏɴᴛʀᴏʟ</gradient> &8» "
```

### 2. HEX Colors
Use 6-digit hex color codes with the `&#RRGGBB` format:
```yaml
title: "&#B499DD&lꜰʀᴏᴢᴇɴ"
```

### 3. Legacy Color Codes
Standard Minecraft ampersand color and format codes are fully supported:
```yaml
title: "&c&lUNDER INSPECTION"
```

---

## Placeholders

The following placeholders are supported within notifications:

* `%player%`: Replaced with the suspect's player name.
* `%time%`: Formatted elapsed inspection time (`MM:SS`).
* `%staff%`: Name of the staff member who initiated the inspection.
* `%count%`: Total count of players currently under inspection.
