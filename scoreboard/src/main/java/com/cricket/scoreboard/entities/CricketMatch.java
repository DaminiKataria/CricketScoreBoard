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
@Table(name = "CRICKET_MATCH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CricketMatch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Match_Number")
	private int matchNumber;
	
	@Column(name = "Team_A")
	private String teamA;
	
	@Column(name = "Team_B")
	private String teamB;
	
	@Column(name = "Winner")
	private String winner;
	
	@Column(name = "No_Of_Players")
	private int numberOfPlayers;
	
	@Column(name = "No_Of_Overs")
	private int totalOvers;
	
	 
	

}
