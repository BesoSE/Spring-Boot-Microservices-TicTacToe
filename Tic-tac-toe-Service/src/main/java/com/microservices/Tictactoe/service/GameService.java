package com.microservices.Tictactoe.service;

import com.microservices.Tictactoe.exception.InvalidGameException;
import com.microservices.Tictactoe.exception.InvalidParamException;
import com.microservices.Tictactoe.exception.NotFoundExceptin;
import com.microservices.Tictactoe.model.Game;
import com.microservices.Tictactoe.model.GamePlay;
import com.microservices.Tictactoe.model.Player;

public interface GameService {
    Game createGame(Player player1);
    Game connectToGame(Player player2,Long gameid) throws InvalidParamException, InvalidGameException;
    Game connectToRandomGame(Player player2) throws NotFoundExceptin;
    Game gamePlay(GamePlay gamePlay) throws NotFoundExceptin, InvalidGameException;
    Boolean gameFinished(Long id) throws NotFoundExceptin;

}
