package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.gameEvents.GameEvent;

public interface EventListener {
    /**
     * Receives events
     * @param gameEvent received event
     */
    void receiveEvent(GameEvent gameEvent);
}
