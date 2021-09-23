package com.cricket.scoreboard.models;

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
public class PlayerScoreResult {

	private String playerName;

	private int score;

	private int fours;

	private int sixes;

	private int balls;

}
