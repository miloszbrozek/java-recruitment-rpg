package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.*;
import com.jsofteris.javaDevRecruitment.exceptions.RpgFileException;
import com.jsofteris.javaDevRecruitment.services.interfaces.GameLoader;
import com.jsofteris.javaDevRecruitment.services.interfaces.MoveExecutor;
import com.jsofteris.javaDevRecruitment.services.interfaces.PlayerCreator;

import java.io.*;

public class GameLoaderImpl implements GameLoader {

    private String filesPath;
    private MoveExecutor moveExecutor;
    private PlayerCreator playerCreator;

    public GameLoaderImpl(String filesPath, MoveExecutor moveExecutor, PlayerCreator playerCreator) {
        this.filesPath = filesPath;
        this.moveExecutor = moveExecutor;
        this.playerCreator = playerCreator;
    }

    @Override
    public boolean isSaveFilePresent(String fileName) {
        File f = new File(filesPath + "/" + fileName);
        return f.exists() && f.isFile();
    }

    @Override
    public void save(Game game, String fileName) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filesPath + "/" + fileName)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(game);
            }
        } catch (IOException ex) {
            throw new RpgFileException("Unable to save game.", ex);
        }
    }

    @Override
    public Game load(String fileName) {
        try (FileInputStream fileInputStream = new FileInputStream(filesPath + "/" + fileName)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                return (Game) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new RpgFileException("Unable to load game.", ex);
        }
    }

    @Override
    public Game initNewGame() {
        return new Game.GameBuilder()
                .setGameMap(initMap())
                .setPlayer(initPlayer())
                .build();
    }

    private Tile[] createMapData(int size) {
        Tile[] result = new Tile[size];
        for (int i = 0; i < result.length; ++i) {
            result[i] = new Tile(false, TileType.EMPTY);
        }
        return result;
    }

    private void placeOnMap(Tile[] tiles, int location, TileType tileType) {
        tiles[location].setTileType(tileType);
    }

    private GameMap initMap() {
        Tile[] tiles = createMapData(20);
        placeOnMap(tiles, 0, TileType.PLAYER);
        placeOnMap(tiles, 3, TileType.WOLF);
        placeOnMap(tiles, 8, TileType.WOLF);
        GameMap gameMap = new GameMap(tiles);
        moveExecutor.explore(gameMap);
        return gameMap;
    }

    private Player initPlayer() {
        return playerCreator.createPlayer();
    }

}
