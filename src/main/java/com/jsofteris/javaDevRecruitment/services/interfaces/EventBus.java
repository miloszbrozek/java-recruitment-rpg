package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.gameEvents.GameEvent;

public interface EventBus {
    /**
     * Broadcasts event to all subscribers
     * @param gameEvent broadcasted event
     */
    void broadcastEvent(GameEvent gameEvent);

    /**
     * Subscribes listener to event bus
     * @param eventListener listener being subscribed
     */
    void subscribe(EventListener eventListener);
}
