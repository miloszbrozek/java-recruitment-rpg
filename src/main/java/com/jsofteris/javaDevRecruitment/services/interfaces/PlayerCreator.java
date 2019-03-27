package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.Player;

public interface PlayerCreator {
    /**
     * Walks user through the creation of a new player character
     * @return Newly created player character
     */
    Player createPlayer();
}
