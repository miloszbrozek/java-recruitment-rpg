package com.jsofteris.javaDevRecruitment.services;

import com.jsofteris.javaDevRecruitment.entities.GameMap;
import com.jsofteris.javaDevRecruitment.entities.PlayerMove;
import com.jsofteris.javaDevRecruitment.entities.Tile;
import com.jsofteris.javaDevRecruitment.entities.TileType;
import com.jsofteris.javaDevRecruitment.entities.gameEvents.EnemyKilledEvent;
import com.jsofteris.javaDevRecruitment.entities.gameEvents.GameWonEvent;
import com.jsofteris.javaDevRecruitment.services.implementations.MoveExecutorImpl;
import com.jsofteris.javaDevRecruitment.services.interfaces.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoveExecutorImplTest {

    @Spy
    private EventBus eventBus;

    @Before
    public void setUp() {
        moveExecutor = new MoveExecutorImpl(eventBus);
        allMovesValid = new ArrayList<>(Arrays.asList(PlayerMove.FightLeft, PlayerMove.FightRight, PlayerMove.MoveLeft, PlayerMove.MoveRight));
        playerWithWolves = new GameMap(new Tile[]{
                new Tile(false, TileType.EMPTY),
                new Tile(false, TileType.WOLF),
                new Tile(false, TileType.PLAYER),
                new Tile(false, TileType.WOLF),
                new Tile(false, TileType.EMPTY),
        });
        playerOnEmptyMap = new GameMap(new Tile[]{
                new Tile(false, TileType.EMPTY),
                new Tile(false, TileType.EMPTY),
                new Tile(false, TileType.EMPTY),
                new Tile(false, TileType.PLAYER),
                new Tile(false, TileType.EMPTY),
                new Tile(false, TileType.EMPTY),
                new Tile(false, TileType.EMPTY),
        });
    }

    private MoveExecutorImpl moveExecutor;
    private List<PlayerMove> allMovesValid;
    private GameMap playerWithWolves;
    private GameMap playerOnEmptyMap;


    @Test(expected = IllegalArgumentException.class)
    public void movePlayer_throwsIAEOnInvalidMove() {
        moveExecutor.movePlayer(playerWithWolves, PlayerMove.MoveLeft);
    }

    private int getPlayerLocation(GameMap map) {
        for (int i = 0; i < map.getTiles().length; ++i) {
            if (map.getTiles()[i].getTileType() == TileType.PLAYER) {
                return i;
            }
        }
        return -1;
    }

    @Test
    public void movePlayer_moveLeft() {
        int locationBefore = getPlayerLocation(playerOnEmptyMap);
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveRight);
        int locationAfter = getPlayerLocation(playerOnEmptyMap);

        assertEquals(locationBefore + 1, locationAfter);
    }

    @Test
    public void movePlayer_moveRight() {
        int locationBefore = getPlayerLocation(playerOnEmptyMap);
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveLeft);
        int locationAfter = getPlayerLocation(playerOnEmptyMap);

        assertEquals(locationBefore - 1, locationAfter);
    }

    @Test
    public void movePlayer_fightLeftWolfDisappears() {
        int playerLocation = getPlayerLocation(playerWithWolves);
        moveExecutor.movePlayer(playerWithWolves, PlayerMove.FightLeft);

        Tile tileToTheLeft = playerWithWolves.getTiles()[playerLocation - 1];
        Tile tileToTheRight = playerWithWolves.getTiles()[playerLocation + 1];
        assertEquals(TileType.EMPTY, tileToTheLeft.getTileType());
        assertEquals(TileType.WOLF, tileToTheRight.getTileType());
    }

    @Test
    public void movePlayer_fightRightWolfDisappears() {
        int playerLocation = getPlayerLocation(playerWithWolves);
        moveExecutor.movePlayer(playerWithWolves, PlayerMove.FightRight);

        Tile tileToTheLeft = playerWithWolves.getTiles()[playerLocation - 1];
        Tile tileToTheRight = playerWithWolves.getTiles()[playerLocation + 1];
        assertEquals(TileType.WOLF, tileToTheLeft.getTileType());
        assertEquals(TileType.EMPTY, tileToTheRight.getTileType());
    }

    @Test
    public void movePlayer_fightEnemyKillEventGenerated() {
        int playerLocation = getPlayerLocation(playerWithWolves);
        moveExecutor.movePlayer(playerWithWolves, PlayerMove.FightLeft);

        verify(eventBus).broadcastEvent(any(EnemyKilledEvent.class));
    }

    @Test
    public void explore_mapIsExplored() {
        int playerLocation = getPlayerLocation(playerOnEmptyMap);

        moveExecutor.explore(playerOnEmptyMap);

        Tile[] tiles = playerOnEmptyMap.getTiles();
        Tile prevTile = tiles[playerLocation - 2];
        Tile tileToTheLeft = tiles[playerLocation - 1];
        Tile playerTile = tiles[playerLocation];
        Tile tileToTheRight = tiles[playerLocation + 1];
        Tile nextTile = tiles[playerLocation + 2];

        assertEquals(false, prevTile.isExplored());
        assertEquals(true, tileToTheLeft.isExplored());
        assertEquals(true, playerTile.isExplored());
        assertEquals(true, tileToTheRight.isExplored());
        assertEquals(false, nextTile.isExplored());
    }

    @Test
    public void explore_gameWonEventGeneratedWhenMapExplored() {
        moveExecutor.explore(playerOnEmptyMap);

        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveLeft);
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveLeft);
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveRight);
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveRight);
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveRight);

        verify(eventBus, times(0)).broadcastEvent(any(GameWonEvent.class));
        moveExecutor.movePlayer(playerOnEmptyMap, PlayerMove.MoveRight);
        verify(eventBus, times(1)).broadcastEvent(any(GameWonEvent.class));
    }

    @Test
    public void getValidPlayerMoves_checkFightMoves() {
        List<PlayerMove> moves = moveExecutor.getValidPlayerMoves(playerWithWolves);

        assertEquals(moves.size(), 2);
        assertTrue(moves.contains(PlayerMove.FightLeft));
        assertTrue(moves.contains(PlayerMove.FightRight));
    }

    @Test
    public void getValidPlayerMoves_checkTransitionMoves() {
        List<PlayerMove> moves = moveExecutor.getValidPlayerMoves(playerOnEmptyMap);

        assertEquals(moves.size(), 2);
        assertTrue(moves.contains(PlayerMove.MoveLeft));
        assertTrue(moves.contains(PlayerMove.MoveRight));
    }

}