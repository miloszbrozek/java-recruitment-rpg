package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.GameMap;
import com.jsofteris.javaDevRecruitment.entities.PlayerMove;

import java.util.List;

public interface MoveExecutor {
    /**
     * Executes a specified player move.
     * @param gameMap current game
     * @param playerMove player move to be done
     */
    void movePlayer(GameMap gameMap, PlayerMove playerMove);

    /**
     * Explores nearest tiles according to player's range of sight
     * @param gameMap current game
     */
    void explore(GameMap gameMap);

    /**
     * Returns a list of valid player moves
     * @param gameMap current game
     * @return list of valid player moves
     */
    List<PlayerMove> getValidPlayerMoves(GameMap gameMap);
}
