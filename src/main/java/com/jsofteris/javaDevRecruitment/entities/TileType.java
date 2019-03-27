package com.jsofteris.javaDevRecruitment.entities;

public enum TileType {
    EMPTY("E", true, -1, false),
    PLAYER("P", false, -1, false),
    WOLF("W", false, 100, true);

    private String code;
    private boolean walkable;
    private int expReward;
    private boolean killable;

    TileType(String code, boolean walkable, int expReward, boolean killable) {
        this.code = code;
        this.walkable = walkable;
        this.expReward = expReward;
        this.killable = killable;
    }

    public String getCode() {
        return code;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public int getExpReward() {
        return expReward;
    }

    public boolean isKillable() {
        return killable;
    }
}
