package com.jsofteris.javaDevRecruitment.entities.gameEvents;

public class EnemyKilledEvent extends GameEvent {
    private String enemyName;
    private int expGained;

    public String getEnemyName() {
        return enemyName;
    }

    public int getExpGained() {
        return expGained;
    }

    public EnemyKilledEvent(String enemyName, int expGained) {
        super(GameEventType.EnemyKilled);
        this.enemyName = enemyName;
        this.expGained = expGained;
    }
}
