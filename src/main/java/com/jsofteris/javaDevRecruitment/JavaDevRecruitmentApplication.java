package com.jsofteris.javaDevRecruitment;

import com.jsofteris.javaDevRecruitment.services.implementations.*;
import com.jsofteris.javaDevRecruitment.services.interfaces.*;

import java.nio.file.Paths;

public class JavaDevRecruitmentApplication {

    private static final String SAVED_GAME_LOCATION = "savedGame.sav";

    public static void main(String[] args) {
        GameFlow gameFlow = initGameFlow();
        gameFlow.play();
    }

    private static GameFlow initGameFlow() {
        Prompter prompter = new PrompterImpl();
        EventBus eventBus = new EventBusImpl();
        MoveExecutor moveExecutor = new MoveExecutorImpl(eventBus);
        PlayerCreator playerCreator = new PlayerCreatorImpl(prompter);
        GameLoader gameLoader = new GameLoaderImpl(JavaDevRecruitmentApplication.getFilesPath(), moveExecutor, playerCreator);
        GameRenderer gameRenderer = new GameRendererImpl(prompter);
        GameFlowImpl gameFlow = new GameFlowImpl(gameRenderer, moveExecutor, gameLoader, prompter, SAVED_GAME_LOCATION);
        eventBus.subscribe(gameFlow);

        return gameFlow;
    }

    private static String getFilesPath() {
        return Paths.get("").toAbsolutePath().toString();
    }

}
