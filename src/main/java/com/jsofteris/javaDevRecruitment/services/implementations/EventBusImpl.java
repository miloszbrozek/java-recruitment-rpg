package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.gameEvents.GameEvent;
import com.jsofteris.javaDevRecruitment.services.interfaces.EventBus;
import com.jsofteris.javaDevRecruitment.services.interfaces.EventListener;

import java.util.ArrayList;
import java.util.List;

public class EventBusImpl implements EventBus {

    List<EventListener> listeners = new ArrayList<>();

    @Override
    public void broadcastEvent(GameEvent gameEvent) {
        listeners.stream().forEach(l -> l.receiveEvent(gameEvent));
    }

    @Override
    public void subscribe(EventListener eventListener) {
        listeners.add(eventListener);
    }
}
