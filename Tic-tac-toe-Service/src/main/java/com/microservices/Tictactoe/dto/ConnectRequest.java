package com.microservices.Tictactoe.dto;

import com.microservices.Tictactoe.model.Game;
import com.microservices.Tictactoe.model.Player;

public class ConnectRequest {
    private Player player;
    private Long gameid;

    public ConnectRequest() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getGameid() {
        return gameid;
    }

    public void setGameid(Long gameid) {
        this.gameid = gameid;
    }
}
