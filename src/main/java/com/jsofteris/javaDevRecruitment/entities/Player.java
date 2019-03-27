package com.jsofteris.javaDevRecruitment.entities;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String nickName;
    private int experience;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Player(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
        experience = 0;
    }

    public Player() {
        experience = 0;
    }
}
