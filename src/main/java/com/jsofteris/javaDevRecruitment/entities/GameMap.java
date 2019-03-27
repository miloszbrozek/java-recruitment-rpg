package com.jsofteris.javaDevRecruitment.entities;

import java.io.Serializable;

public class GameMap implements Serializable {
    private Tile[] tiles;

    public GameMap(Tile[] tiles) {
        if (tiles == null) {
            throw new NullPointerException("Cannot create a map with null tiles");
        }
        this.tiles = tiles;
    }

    public GameMap() {

    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }
}
