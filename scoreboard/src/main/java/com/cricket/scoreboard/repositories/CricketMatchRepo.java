package com.cricket.scoreboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cricket.scoreboard.entities.CricketMatch;

@Repository
public interface CricketMatchRepo extends JpaRepository<CricketMatch,Integer>{

}
