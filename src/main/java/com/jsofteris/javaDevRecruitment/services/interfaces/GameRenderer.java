package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.Game;

public interface GameRenderer {
    /**
     * Renders game screen
     * @param game game to be rendered
     */
    void render(Game game);
}
