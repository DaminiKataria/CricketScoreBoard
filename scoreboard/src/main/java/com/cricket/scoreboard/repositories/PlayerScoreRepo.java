package com.cricket.scoreboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cricket.scoreboard.entities.PlayerScore;

@Repository
public interface PlayerScoreRepo extends JpaRepository<PlayerScore,Integer>{
	

}
