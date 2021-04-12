package com.microservices.Tictactoe.service;

import com.microservices.Tictactoe.exception.InvalidGameException;
import com.microservices.Tictactoe.exception.InvalidParamException;
import com.microservices.Tictactoe.exception.NotFoundExceptin;
import com.microservices.Tictactoe.model.Game;
import com.microservices.Tictactoe.model.GamePlay;
import com.microservices.Tictactoe.model.Player;
import com.microservices.Tictactoe.model.TicToe;
import com.microservices.Tictactoe.repository.GameRepository;
import com.microservices.Tictactoe.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.microservices.Tictactoe.model.GameStatus.*;

@Service
public class GameServiceImpl implements GameService{
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Game createGame(Player player1) {
        Game game=new Game();
        Player p=playerRepository.findByUserName(player1.getUserName());
        game.setPlayer1(p);
        game.setStatus(NEW);
        return gameRepository.save(game);
    }

    @Override
    public Game connectToGame(Player player2,Long gameid) throws InvalidParamException, InvalidGameException {
        Game game=gameRepository.findById(gameid).orElseThrow( ()->new InvalidParamException("There is no game with id" +gameid));
    if(game.getPlayer2()!=null){
        throw new InvalidGameException("You can't add more players");
    }

        Player p=playerRepository.findByUserName(player2.getUserName());
        game.setPlayer1(p);
        game.setPlayer2(p);
    game.setStatus(IN_PROGRESS);
        return gameRepository.save(game);
    }

    @Override
    public Game connectToRandomGame(Player player2) throws NotFoundExceptin {
        List<Game> games=gameRepository.findAll();


      Game game= games.stream().filter(g->g.getStatus().equals(NEW)).findFirst().
              orElseThrow(()->new NotFoundExceptin("There is no game to play random"));
        Player p=playerRepository.findByUserName(player2.getUserName());

      game.setPlayer2(p);
       game.setStatus(IN_PROGRESS);
       return gameRepository.save(game);
    }

    @Override
    public Game gamePlay(GamePlay gamePlay) throws NotFoundExceptin, InvalidGameException {
        Game game=gameRepository.findById(gamePlay.getGame().getId()).orElseThrow(()->new NotFoundExceptin("Game not found"));
        if(game.getStatus().equals(FINISHED)){
            throw new InvalidGameException("Game is already finished");
        }
        int [][] board=game.getBoard();

        board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()]=gamePlay.getType().getValue();


        Boolean xW=checkWinner(game.getBoard(), TicToe.X);
        Boolean oW=checkWinner(game.getBoard(), TicToe.O);

       if(xW==true){

          game.setWinner(TicToe.X);
          game.setStatus(FINISHED);

       }else if(oW==true){

            game.setWinner(TicToe.O);
           game.setStatus(FINISHED);
        }

        return gameRepository.save(game);


    }

    private boolean checkWinner(int[][] board, TicToe tictoe) {
        int[] boardArray=new int[9];
        int counterI=0;
        for(int i=0; i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
                boardArray[counterI]=board[i][j];
                counterI++;
            }
        }
        int [][] winComb={{0,1,2},
                {3,4,5},
                {6,7,8},
                {0,3,6},
                {1,4,7},
                {2,5,8},
                {0,4,8},
                {2,4,6}
        };
        for(int i=0; i<winComb.length;i++){
            int counter=0;
            for(int j=0; j<winComb[i].length; j++){
                if(boardArray[winComb[i][j]]==tictoe.getValue()) {
                    counter++;
                }
                if(counter==3){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean gameFinished(Long id) throws NotFoundExceptin {
        Game game=gameRepository.findById(id).orElseThrow(()->new NotFoundExceptin("There is no game wit id "+id));
        game.setStatus(FINISHED);
        try{
            gameRepository.save(game);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
