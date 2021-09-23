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
@Table(name = "TEAM_PLAYERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeamPlayers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Team_Players_Id")
	private int teamPlayersId;
	
	@Column(name = "Team_Name")
	private String teamName;
	
	@Column(name = "Team_Id")
	private int teamId;
	
	@Column(name = "Player_Id")
	private int playerId;
	
	@Column(name = "Player_Name")
	private String playerName;
	
	@Column(name = "Batting_Position")
	private int battingPosition;

}
