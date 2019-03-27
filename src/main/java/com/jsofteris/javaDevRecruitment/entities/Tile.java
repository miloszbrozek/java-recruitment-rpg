package com.jsofteris.javaDevRecruitment.entities;

import java.io.Serializable;

public class Tile implements Serializable {
    private boolean explored;
    private TileType tileType;

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Tile(boolean explored, TileType tileType) {
        this.explored = explored;
        this.tileType = tileType;
    }

    public Tile() {

    }
}
