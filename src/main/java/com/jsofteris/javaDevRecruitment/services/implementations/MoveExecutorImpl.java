package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.GameMap;
import com.jsofteris.javaDevRecruitment.entities.PlayerMove;
import com.jsofteris.javaDevRecruitment.entities.Tile;
import com.jsofteris.javaDevRecruitment.entities.TileType;
import com.jsofteris.javaDevRecruitment.entities.gameEvents.EnemyKilledEvent;
import com.jsofteris.javaDevRecruitment.entities.gameEvents.GameWonEvent;
import com.jsofteris.javaDevRecruitment.services.interfaces.EventBus;
import com.jsofteris.javaDevRecruitment.services.interfaces.MoveExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveExecutorImpl implements MoveExecutor {

    private EventBus eventBus;

    public MoveExecutorImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void movePlayer(GameMap gameMap, PlayerMove playerMove) {
        validateMove(gameMap, playerMove);
        executeMove(gameMap, playerMove);
        explore(gameMap);
        boolean gameFinished = isGameFinished(gameMap);
        if (gameFinished) {
            eventBus.broadcastEvent(new GameWonEvent());
        }
    }

    private void executeMove(GameMap gameMap, PlayerMove playerMove) {
        int playerLocation = getPlayerLocation(gameMap);
        switch (playerMove) {
            case MoveLeft:
                movePlayer(gameMap, playerLocation, playerLocation - 1);
                break;
            case MoveRight:
                movePlayer(gameMap, playerLocation, playerLocation + 1);
                break;
            case FightLeft:
                fight(gameMap, playerLocation - 1);
                break;
            case FightRight:
                fight(gameMap, playerLocation + 1);
                break;
            default:
                throw new IllegalArgumentException("Not handled player move: " + playerMove);
        }
    }

    private void validateMove(GameMap gameMap, PlayerMove playerMove) {
        List<PlayerMove> validMoves = getValidPlayerMoves(gameMap);
        boolean isValidMove = validMoves.stream().anyMatch(vm -> vm == playerMove);
        if (!isValidMove) {
            throw new IllegalArgumentException("Invalid player move at this moment: " + playerMove);
        }
    }

    private void fight(GameMap gameMap, int enemyLocation) {
        Tile[] tiles = gameMap.getTiles();
        Tile enemyTile = tiles[enemyLocation];
        TileType enemyTileType = enemyTile.getTileType();
        enemyTile.setTileType(TileType.EMPTY);
        eventBus.broadcastEvent(new EnemyKilledEvent(enemyTileType.toString(), enemyTileType.getExpReward()));
    }

    private void movePlayer(GameMap gameMap, int from, int to) {
        Tile[] tiles = gameMap.getTiles();
        tiles[from].setTileType(TileType.EMPTY);
        tiles[to].setTileType(TileType.PLAYER);
    }

    private boolean isGameFinished(GameMap gameMap) {
        boolean allTilesExplored = Arrays.stream(gameMap.getTiles())
                .allMatch(tile -> tile.isExplored());
        return allTilesExplored;
    }

    @Override
    public void explore(GameMap gameMap) {
        int playerLocation = getPlayerLocation(gameMap);
        Tile[] tiles = gameMap.getTiles();
        for (int i = playerLocation - 1; i < tiles.length && i <= playerLocation + 1; ++i) {
            if (i < 0) {
                continue;
            }
            tiles[i].setExplored(true);
        }
    }

    @Override
    public List<PlayerMove> getValidPlayerMoves(GameMap gameMap) {
        int playerLocation = getPlayerLocation(gameMap);
        List<PlayerMove> leftMoves = getValidMovesForLocation(gameMap, playerLocation - 1, PlayerMove.MoveLeft, PlayerMove.FightLeft);
        List<PlayerMove> rightMoves = getValidMovesForLocation(gameMap, playerLocation + 1, PlayerMove.MoveRight, PlayerMove.FightRight);

        List<PlayerMove> result = new ArrayList<>(leftMoves);
        result.addAll(rightMoves);
        return result;
    }

    private List<PlayerMove> getValidMovesForLocation(GameMap gameMap, int location, PlayerMove moveCode, PlayerMove fightCode) {
        Tile[] tiles = gameMap.getTiles();
        List<PlayerMove> result = new ArrayList<>();
        if (location >= 0 && location < tiles.length) {
            Tile tile = tiles[location];
            if (tile.getTileType().isKillable()) {
                result.add(fightCode);
            }
            if (tile.getTileType().isWalkable()) {
                result.add(moveCode);
            }
        }
        return result;
    }

    private int getPlayerLocation(GameMap gameMap) {
        for (int i = 0; i < gameMap.getTiles().length; ++i) {
            if (gameMap.getTiles()[i].getTileType() == TileType.PLAYER) {
                return i;
            }
        }
        throw new IllegalArgumentException("Cannot get player location. Player was not found on the gameMap.");
    }
}
