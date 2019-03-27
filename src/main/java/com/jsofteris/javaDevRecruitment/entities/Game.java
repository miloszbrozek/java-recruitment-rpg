package com.jsofteris.javaDevRecruitment.entities;

import java.io.Serializable;

public class Game implements Serializable {
    private GameMap gameMap;
    private Player player;

    public GameMap getGameMap() {
        return gameMap;
    }

    public Player getPlayer() {
        return player;
    }

    public Game() {

    }

    public Game(GameMap gameMap, Player player) {
        this.gameMap = gameMap;
        this.player = player;
    }

    public static class GameBuilder {
        private GameMap gameMap;
        private Player player;

        public GameBuilder setGameMap(GameMap gameMap) {
            this.gameMap = gameMap;
            return this;
        }

        public GameBuilder setPlayer(Player player) {
            this.player = player;
            return this;
        }

        public Game build() {
            validateNotNull(gameMap, "gameMap");
            validateNotNull(player, "player");
            return new Game(gameMap, player);
        }

        private void validateNotNull(Object obj, String name) {
            if (obj == null) {
                throw new IllegalArgumentException("Cannot create a game with null " + name);
            }
        }
    }
}
