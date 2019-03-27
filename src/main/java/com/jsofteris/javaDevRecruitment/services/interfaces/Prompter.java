package com.jsofteris.javaDevRecruitment.services.interfaces;

import com.jsofteris.javaDevRecruitment.entities.MenuOption;

import java.util.List;

public interface Prompter {
    /**
     * Asks user for a string input
     * @param prompt Text displayed to the user, e.g. "Please enter your name"
     * @return input gathered from user
     */
    String askForString(String prompt);

    /**
     * Asks user for a string input. Provided value has to be one of allowed values
     * @param prompt Text displayed to the user, e.g. "Please enter your name"
     * @param allowedValues List of allowed values user can input
     * @return input gathered from user
     */
    String askForString(String prompt, List<String> allowedValues);

    /**
     * Outputs a string to the user
     * @param text String that is displayed to the user
     */
    void writeLine(String text);

    /**
     * Displays menu options and gets input from the user. The prompt is presented until user enters correct value
     * @param menuOptions available menu options
     * @return option chosen by the user
     */
    MenuOption displayMenuAndGetInput(List<MenuOption> menuOptions);
}
