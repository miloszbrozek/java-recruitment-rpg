package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.Game;
import com.jsofteris.javaDevRecruitment.entities.GameMap;
import com.jsofteris.javaDevRecruitment.entities.Player;
import com.jsofteris.javaDevRecruitment.entities.TileType;
import com.jsofteris.javaDevRecruitment.services.interfaces.GameRenderer;
import com.jsofteris.javaDevRecruitment.services.interfaces.Prompter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GameRendererImpl implements GameRenderer {

    private Prompter prompter;

    public GameRendererImpl(Prompter prompter) {
        this.prompter = prompter;
    }

    @Override
    public void render(Game game) {
        prompter.writeLine(renderPlayerToString(game.getPlayer()));
        prompter.writeLine(renderMapToString(game.getGameMap()));
    }

    private String mapTileTypeToView(TileType tileType, boolean isExplored) {
        if (!isExplored) {
            return "█";
        }
        switch (tileType) {
            case EMPTY:
                return "░";
            case PLAYER:
                return "P";
            case WOLF:
                return "W";
            default:
                throw new IllegalArgumentException("Cannot draw unknown tileType: " + tileType.toString());
        }
    }

    private String renderPlayerToString(Player player) {
        StringBuilder result = new StringBuilder();
        result.append("\nPlayer\n");
        result.append("Name: " + player.getName() + " the " + player.getNickName() + "\n");
        result.append("Experience: " + player.getExperience() + "\n");
        return result.toString();
    }

    private String renderMapToString(GameMap gameMap) {
        StringBuilder result = new StringBuilder();
        result.append("MAP STARTS HERE --->");
        String renderedMap = Arrays.stream(gameMap.getTiles())
                .map(tile -> mapTileTypeToView(tile.getTileType(), tile.isExplored()))
                .collect(Collectors.joining());
        result.append(renderedMap);
        result.append("<--- MAP ENDS HERE");
        return result.toString();
    }
}
