package me.twins.control.model;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;

import java.util.UUID;

public class ControlledSession {
    private final UUID playerUuid;
    private final String playerName;
    private final String staffName;
    private final Location originalLocation;
    private final long startTimeMillis;
    private BossBar bossBar;

    public ControlledSession(UUID playerUuid, String playerName, String staffName, Location originalLocation) {
        this.playerUuid = playerUuid;
        this.playerName = playerName;
        this.staffName = staffName;
        this.originalLocation = originalLocation;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getStaffName() {
        return staffName;
    }

    public Location getOriginalLocation() {
        return originalLocation;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public long getDurationSeconds() {
        return (System.currentTimeMillis() - startTimeMillis) / 1000;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }
}
