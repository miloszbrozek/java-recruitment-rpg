package com.jsofteris.javaDevRecruitment.entities;

public enum MenuOption {
    StartNewGame("NEW_GAME", "Starts new game"),
    ResumeGame("RESUME_GAME", "Resumes previously saved game"),
    SaveGame("SAVE_GAME", "Saves current game state"),
    ExitGame("EXIT_GAME", "Exit game"),
    Exit("EXIT", "Exit"),
    MoveLeft("L", "Moves player to the left"),
    MoveRight("R", "Moves player to the right"),
    FightLeft("FL", "Player kills what is on the left"),
    FightRight("FR", "Player kills what is on the right");

    private String code;
    private String helpText;

    public String getHelpText() {
        return helpText;
    }

    public String getCode() {
        return code;
    }

    private MenuOption(String code, String helpText) {
        this.helpText = helpText;
        this.code = code;
    }
}
