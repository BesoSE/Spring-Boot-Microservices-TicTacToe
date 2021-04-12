package com.microservices.Tictactoe.model;

public class GamePlay {
    private TicToe type;
    private Integer coordinateX;
    private Integer coordinateY;
    private Game game;

    public GamePlay() {
    }

    public TicToe getType() {
        return type;
    }

    public void setType(TicToe type) {
        this.type = type;
    }

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
