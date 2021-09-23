package com.cricket.scoreboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cricket.scoreboard.entities.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team,Integer>{

	
}
