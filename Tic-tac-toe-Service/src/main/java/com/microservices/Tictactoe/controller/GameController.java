package com.microservices.Tictactoe.controller;

import com.microservices.Tictactoe.dto.ConnectRequest;
import com.microservices.Tictactoe.dto.LoginRequest;
import com.microservices.Tictactoe.dto.LoginResponse;
import com.microservices.Tictactoe.exception.InvalidGameException;
import com.microservices.Tictactoe.exception.InvalidParamException;
import com.microservices.Tictactoe.exception.NotFoundExceptin;
import com.microservices.Tictactoe.model.Game;
import com.microservices.Tictactoe.model.GamePlay;
import com.microservices.Tictactoe.model.Player;
import com.microservices.Tictactoe.proxy.LoginProxy;
import com.microservices.Tictactoe.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;
    private  final SimpMessagingTemplate simpMessagingTemplate;
    private final LoginProxy proxy;

    public GameController(GameService gameService, SimpMessagingTemplate simpMessagingTemplate, LoginProxy proxy) {
        this.gameService = gameService;
        this.simpMessagingTemplate=simpMessagingTemplate;
        this.proxy = proxy;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse=proxy.retriveDataForEmail(loginRequest);
        System.out.println();
        return ResponseEntity.ok(loginResponse);

    }
    @PostMapping("/finish")
    public ResponseEntity<Boolean> finish(@RequestBody Map<String,Long> id) throws NotFoundExceptin {


       Boolean game=gameService.gameFinished(id.get("id"));
        return ResponseEntity.ok(game);

    }


    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player){
        System.out.println(player+" is create a new game");
        return ResponseEntity.ok(gameService.createGame(player));
    }


    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws InvalidParamException, InvalidGameException {
        System.out.println("Try to connect "+request);
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(),request.getGameid()));
    }

    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws NotFoundExceptin {
        System.out.println("connect random "+player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    @PostMapping("/gameplay")
    public ResponseEntity<Game> play(@RequestBody GamePlay gamePlay) throws NotFoundExceptin, InvalidGameException {


        System.out.println("gameplay "+gamePlay.getType().getValue());
        Game game=gameService.gamePlay(gamePlay);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/"+game.getId(),game);

    return ResponseEntity.ok(game);
    }
}
