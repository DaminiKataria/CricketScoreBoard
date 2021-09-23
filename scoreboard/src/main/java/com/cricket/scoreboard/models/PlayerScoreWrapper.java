package com.cricket.scoreboard.models;

import com.cricket.scoreboard.entities.PlayerScore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerScoreWrapper {

	 PlayerScore playerOnStrike;
	  
	 PlayerScore playerOnField;
	 
	 int totalScore;
	 
}
