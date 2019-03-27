package com.jsofteris.javaDevRecruitment.services.implementations;

import com.jsofteris.javaDevRecruitment.entities.MenuOption;
import com.jsofteris.javaDevRecruitment.services.interfaces.Prompter;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrompterImpl implements Prompter {

    private Scanner scanner;

    public PrompterImpl() {
        scanner = new Scanner(System.in);
    }

    public String askForString(String prompt) {
        return askForString(prompt, null);
    }

    @Override
    public String askForString(String prompt, List<String> allowedValues) {
        String answer;
        while(true) {
            System.out.println(prompt);
            answer = scanner.nextLine();
            if(isValidAnswer(answer, allowedValues)){
                break;
            } else {
                writeLine("\nIncorrect answer, please try one more time!!!\n");
            }
        }
        return answer;
    }

    private boolean isValidAnswer(String value, List<String> allowedValues) {
        if (value == null || value.length() == 0) {
            return false;
        }
        if (allowedValues != null) {
            return allowedValues.stream()
                    .anyMatch(av -> Objects.equals(av, value));
        } else {
            return true;
        }
    }

    public void writeLine(String text) {
        System.out.println(text);
    }

    @Override
    public MenuOption displayMenuAndGetInput(List<MenuOption> menuOptions) {
        String menuText = "\nType one of the following and press enter:";
        String menuItemsText = menuOptions.stream()
                .map(menuOption -> "\n" + menuOption.getCode() + " - " + menuOption.getHelpText())
                .collect(Collectors.joining());
        List<String> validCodes = menuOptions.stream()
                .map(menuOption -> menuOption.getCode())
                .collect(Collectors.toList());

        String userInput = askForString(menuText + menuItemsText, validCodes);
        return menuOptions.stream()
                .filter(menuOption -> Objects.equals(menuOption.getCode(), userInput))
                .findFirst()
                .get();
    }
}
