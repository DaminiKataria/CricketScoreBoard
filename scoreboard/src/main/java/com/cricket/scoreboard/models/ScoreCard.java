package com.cricket.scoreboard.models;

import java.util.List;

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
public class ScoreCard {

	private List<PlayerScoreResult> playerScoreList;
	private int totalScore;
	private double overs;
	private int wickets;
}
