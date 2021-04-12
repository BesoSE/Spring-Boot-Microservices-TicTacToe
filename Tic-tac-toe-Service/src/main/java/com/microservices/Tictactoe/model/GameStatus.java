package com.microservices.Tictactoe.model;

public enum GameStatus {
    NEW(0),IN_PROGRESS(1),FINISHED(2);
    private Integer value;

    GameStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
