package com.tenzo.promote_project.domain;

public class Customer {

    int id;

    String name;

    int promoteAvalable;

    int remainingPromoteTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPromoteAvalable() {
        return promoteAvalable;
    }

    public void setPromoteAvalable(int promoteAvalable) {
        this.promoteAvalable = promoteAvalable;
    }

    public int getRemainingPromoteTime() {
        return remainingPromoteTime;
    }

    public void setRemainingPromoteTime(int remainingPromoteTime) {
        this.remainingPromoteTime = remainingPromoteTime;
    }
}
