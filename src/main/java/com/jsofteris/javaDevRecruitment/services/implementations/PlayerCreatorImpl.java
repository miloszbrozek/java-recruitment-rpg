package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.Player;
import com.jsofteris.javaDevRecruitment.services.interfaces.PlayerCreator;
import com.jsofteris.javaDevRecruitment.services.interfaces.Prompter;

public class PlayerCreatorImpl implements PlayerCreator {

    private Prompter prompter;

    public PlayerCreatorImpl(Prompter prompter) {
        this.prompter = prompter;
    }

    public Player createPlayer() {
        prompter.writeLine("Creating a player character");
        String name = prompter.askForString("What is your name");
        String nickname = prompter.askForString("What is your nickname (e.g. Great, Lame, etc.)");
        prompter.writeLine("A player character created. Name: " + name + ", nickname: " + nickname);
        return new Player(name, nickname);
    }
}
