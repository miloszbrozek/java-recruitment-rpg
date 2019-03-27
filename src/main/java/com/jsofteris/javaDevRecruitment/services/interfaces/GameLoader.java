package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.Game;

public interface GameLoader {
    /**
     * Saves game state in file
     * @param game game to be saved
     * @param fileName file location
     */
    void save(Game game, String fileName);

    /**
     * Checks if saved game file is present
     * @param fileName file location
     * @return - returns true if game file is present
     */
    boolean isSaveFilePresent(String fileName);

    /**
     * Loads saved game from file
     * @param fileName file location
     * @return Loaded game state
     */
    Game load(String fileName);

    /**
     * Initializes new game
     * @return new game object
     */
    Game initNewGame();
}
