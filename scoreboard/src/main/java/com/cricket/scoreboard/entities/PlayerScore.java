package com.cricket.scoreboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PLAYER_SCORE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayerScore {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Player_Score_Id")
	private int playerScoreId;
	
	@Column(name = "Player_Id")
	private int playerId;
	
	@Column(name = "Player_Name")
	private String playerName;
	
	@Column(name = "Team_Id")
	private int teamId;
	
	@Column(name = "Team_Name")
	private String teamName;
	
	@Column(name = "Score")
	private int score;
	
	@Column(name = "Fours")
	private int fours;
	
	@Column(name = "Sixes")
	private int sixes;
	
	@Column(name = "Balls")
	private int balls;
	
	@Column(name = "Strike")
	private String strike;
	
	@Column(name = "In_Field")
	private String inField;

	@Column(name = "Battiing")
	private String batting;
	
	@Column(name = "Batting_Position")
	private int battingPosition;
	

}
