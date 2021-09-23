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
@Table(name = "TEAM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Team_Id")
	private int teamId;
	
	@Column(name = "Team_Name")
	private String teamName;
	
	@Column(name = "No_Of_Players")
	private int numberOfPlayers;
	
	@Column(name = "Overs")
	private int overs;
	
	@Column(name = "wickets")
	private int wickets;
	
	@Column(name = "Total_Score")
	private int totalScore;

	
	
	
}
