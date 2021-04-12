package com.microservices.Tictactoe.repository;

import com.microservices.Tictactoe.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
    Player findByUserName(String username);
}
