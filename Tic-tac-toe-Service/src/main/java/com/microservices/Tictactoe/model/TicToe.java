package com.microservices.Tictactoe.model;

public enum TicToe {
    X(1) ,O(2);
    private Integer value;

    TicToe(Integer value) {
        this.value=value;
    }
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
