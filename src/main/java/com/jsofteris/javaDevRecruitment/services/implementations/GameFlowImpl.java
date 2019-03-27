package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.Game;
import com.jsofteris.javaDevRecruitment.entities.MenuOption;
import com.jsofteris.javaDevRecruitment.entities.Player;
import com.jsofteris.javaDevRecruitment.entities.PlayerMove;
import com.jsofteris.javaDevRecruitment.entities.gameEvents.EnemyKilledEvent;
import com.jsofteris.javaDevRecruitment.entities.gameEvents.GameEvent;
import com.jsofteris.javaDevRecruitment.exceptions.RpgFileException;
import com.jsofteris.javaDevRecruitment.services.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameFlowImpl implements GameFlow, EventListener {

    private GameRenderer gameRenderer;
    private MoveExecutor moveExecutor;
    private GameLoader gameLoader;
    private Prompter prompter;
    private boolean isGameWonOrCancelled;
    private boolean isExiting;
    private Game game;
    private String savedGameLocation;

    public GameFlowImpl(GameRenderer gameRenderer, MoveExecutor moveExecutor, GameLoader gameLoader, Prompter prompter, String savedGameLocation) {
        this.gameRenderer = gameRenderer;
        this.moveExecutor = moveExecutor;
        this.gameLoader = gameLoader;
        this.prompter = prompter;
        this.savedGameLocation = savedGameLocation;
        isGameWonOrCancelled = false;
        isExiting = false;
    }

    @Override
    public void play() {
        while (!isExiting) {
            MenuOption choice = displayPreGameMenuAndGetInput();
            handleMenuChoice(choice);
            enterGameLoop();
        }
    }

    private void enterGameLoop() {
        isGameWonOrCancelled = false;
        while (!isGameWonOrCancelled && !isExiting) {
            gameRenderer.render(game);
            List<PlayerMove> validMoves = moveExecutor.getValidPlayerMoves(game.getGameMap());
            MenuOption choice = displayInGameMenuAndGetInput(validMoves);
            handleMenuChoice(choice);
        }
    }

    private void handleMenuChoice(MenuOption choice) {
        switch (choice) {
            case FightLeft:
            case MoveLeft:
            case MoveRight:
            case FightRight:
                moveExecutor.movePlayer(game.getGameMap(), mapMenuOptionToPlayerMove(choice));
                break;
            case ResumeGame:
                try {
                    game = gameLoader.load(savedGameLocation);
                    prompter.writeLine("Game successfully loaded");
                } catch (RpgFileException ex) {
                    //normally I would log exception to log file here
                    //but task rules forbid to use any frameworks or libraries
                    //so I can't use e.g. log4j here
                    prompter.writeLine("Failed to load game");
                }

                break;
            case StartNewGame:
                game = gameLoader.initNewGame();
                prompter.writeLine("New game started");
                break;
            case SaveGame:
                try {
                    gameLoader.save(game, savedGameLocation);
                    prompter.writeLine("Game successfully saved");
                } catch (RpgFileException ex) {
                    //normally I would log exception to log file here
                    //but task rules forbid to use any frameworks or libraries
                    //so I can't use e.g. log4j here
                    prompter.writeLine("Failed to save game");
                }
                break;
            case Exit:
                prompter.writeLine("Exiting application");
                isExiting = true;
                break;
            case ExitGame:
                prompter.writeLine("Exiting game");
                isGameWonOrCancelled = true;
                break;
        }
    }

    private MenuOption displayPreGameMenuAndGetInput() {
        List<MenuOption> inGameOptions = new ArrayList<>();
        if (gameLoader.isSaveFilePresent(savedGameLocation)) {
            inGameOptions.add(MenuOption.ResumeGame);
        }
        inGameOptions.add(MenuOption.StartNewGame);
        inGameOptions.add(MenuOption.Exit);

        return prompter.displayMenuAndGetInput(inGameOptions);
    }

    private MenuOption displayInGameMenuAndGetInput(List<PlayerMove> validPlayerMoves) {
        List<MenuOption> playerMoveOptions = validPlayerMoves.stream()
                .map(pm -> mapPlayerMoveToMenuOption(pm))
                .collect(Collectors.toList());

        List<MenuOption> inGameOptions = new ArrayList<>(playerMoveOptions);
        inGameOptions.add(MenuOption.SaveGame);
        inGameOptions.add(MenuOption.ExitGame);

        return prompter.displayMenuAndGetInput(inGameOptions);
    }

    private PlayerMove mapMenuOptionToPlayerMove(MenuOption menuOption) {
        switch (menuOption) {
            case MoveLeft:
                return PlayerMove.MoveLeft;
            case MoveRight:
                return PlayerMove.MoveRight;
            case FightLeft:
                return PlayerMove.FightLeft;
            case FightRight:
                return PlayerMove.FightRight;
            default:
                throw new IllegalArgumentException("Not handled menu option");
        }
    }

    private MenuOption mapPlayerMoveToMenuOption(PlayerMove playerMove) {
        switch (playerMove) {
            case MoveLeft:
                return MenuOption.MoveLeft;
            case MoveRight:
                return MenuOption.MoveRight;
            case FightLeft:
                return MenuOption.FightLeft;
            case FightRight:
                return MenuOption.FightRight;
            default:
                throw new IllegalArgumentException("Not handled player move code: " + playerMove);
        }
    }

    @Override
    public void receiveEvent(GameEvent gameEvent) {
        switch (gameEvent.getEventType()) {
            case GameWon:
                isGameWonOrCancelled = true;
                prompter.writeLine("Whole map is explored. You won. Total experience gained: " + game.getPlayer().getExperience());
                break;
            case EnemyKilled:
                handleEnemyKilled(gameEvent);
                break;
        }
    }

    private void handleEnemyKilled(GameEvent gameEvent) {
        EnemyKilledEvent enemyKilledEvent = (EnemyKilledEvent) gameEvent;
        Player player = game.getPlayer();
        player.setExperience(player.getExperience() + enemyKilledEvent.getExpGained());
        prompter.writeLine("Killed " + enemyKilledEvent.getEnemyName() + ", gained " + enemyKilledEvent.getExpGained() + " experience");
    }
}
