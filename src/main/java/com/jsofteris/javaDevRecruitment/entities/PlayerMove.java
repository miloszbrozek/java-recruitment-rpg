package com.jsofteris.javaDevRecruitment.entities;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum PlayerMove {
    MoveLeft("L"),
    MoveRight("R"),
    FightRight("FR"),
    FightLeft("FL");

    private String code;

    PlayerMove(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PlayerMove fromCode(String code) {
        Optional<PlayerMove> found = Arrays.stream(PlayerMove.values())
                .filter(dir -> Objects.equals(dir.getCode(), code))
                .findFirst();
        if (!found.isPresent()) {
            throw new IllegalArgumentException("There is no PlayerMove for code: " + code);
        } else {
            return found.get();
        }
    }
}
