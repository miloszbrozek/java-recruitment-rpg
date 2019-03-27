package com.jsofteris.javaDevRecruitment.entities.gameEvents;

public class GameEvent {
    private GameEventType eventType;

    public GameEventType getEventType() {
        return eventType;
    }

    protected GameEvent(GameEventType eventType) {
        this.eventType = eventType;
    }
}
