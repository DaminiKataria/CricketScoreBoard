package com.cricket.scoreboard.services.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricket.scoreboard.constants.Constant;
import com.cricket.scoreboard.entities.CricketMatch;
import com.cricket.scoreboard.entities.PlayerScore;
import com.cricket.scoreboard.entities.Team;
import com.cricket.scoreboard.models.MatchDetailsRequest;
import com.cricket.scoreboard.models.TeamDetails;
import com.cricket.scoreboard.repositories.CricketMatchRepo;
import com.cricket.scoreboard.repositories.PlayerScoreRepo;
import com.cricket.scoreboard.repositories.TeamRepo;
import com.cricket.scoreboard.services.MatchDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MatchDetailsServiceImpl implements MatchDetailsService {

	@Autowired
	private TeamRepo teamRepo;

	@Autowired
	private PlayerScoreRepo playerScoreRepo;

	@Autowired
	private CricketMatchRepo cricketMatchRepo;

	@Override
	public void updateMatchDetails(MatchDetailsRequest matchDetailsRequest) {

		// Updating details of match in CricketMatch Table
		updateCricketMatchDetails(matchDetailsRequest);

		// Updating team details
		updateTeamDetails(matchDetailsRequest);
	}

	/**
	 * 
	 * @param matchDetailsRequest
	 * This method is used to persist the current match details
	 */
	private void updateCricketMatchDetails(MatchDetailsRequest matchDetailsRequest) {

		CricketMatch match = new CricketMatch();
		match.setNumberOfPlayers(matchDetailsRequest.getNumberOfPlayers());
		match.setTotalOvers(matchDetailsRequest.getTotalOvers());
		match.setTeamA(matchDetailsRequest.getTeamDetails().get(0).getTeamName());
		match.setTeamB(matchDetailsRequest.getTeamDetails().get(1).getTeamName());
		log.info("Match Details: {}", match);
		cricketMatchRepo.save(match);
	}

	/**
	 * 
	 * @param matchDetailsRequest
	 * 
	 * This method persist the details of the teams in the match
	 */
	private void updateTeamDetails(MatchDetailsRequest matchDetailsRequest) {

		for (TeamDetails teamDetails : matchDetailsRequest.getTeamDetails()) {
			Team team = new Team();
			team.setNumberOfPlayers(matchDetailsRequest.getNumberOfPlayers());
			team.setTeamName(teamDetails.getTeamName());
			team.setOvers(0);
			team.setTotalScore(0);
			team.setWickets(0);
			team = teamRepo.save(team);
			int teamId = team.getTeamId();
			// Update details of players
			updatePlayersDetails(teamDetails, teamId);
		}
	}

	/**
	 * 
	 * @param teamDetails
	 * @param teamId
	 * 
	 * This method persist the players details in a team and their initial scores.
	 */
	private void updatePlayersDetails(TeamDetails teamDetails, int teamId) {

		int position = 1;
		for (String name : teamDetails.getPlayers()) {

			PlayerScore playerScore = new PlayerScore();
			playerScore.setBalls(0);
			playerScore.setFours(0);
			playerScore.setScore(0);
			playerScore.setSixes(0);
			playerScore.setBattingPosition(position);
			playerScore.setTeamName(teamDetails.getTeamName());
			playerScore.setPlayerName(name);
			playerScore.setTeamId(teamId);

			if (teamDetails.getBatting().equals(Constant.Y)) {
				playerScore.setBatting(Constant.Y);
				updatePlayerPositions(playerScore);
			} else {
				playerScore.setBatting(Constant.N);
				playerScore.setInField(Constant.N);
				playerScore.setStrike(Constant.N);
			}
			playerScoreRepo.save(playerScore);
			position++;
		}

	}

	/**
	 * This method updates the status of second team players for batting(Second inning)
	 */
	@Override
	public void updateSecondInningsDetails() {

		List<PlayerScore> playerScoreList = playerScoreRepo.findAll();
		for (PlayerScore playerScore : playerScoreList) {
			if (playerScore.getBatting().equals(Constant.Y)) {
				playerScore.setBatting(Constant.N);
			} else {
				playerScore.setBatting(Constant.Y);
			}
		}
		for (PlayerScore playerScore : playerScoreList) {
			if (playerScore.getBatting().equals(Constant.Y)) {
				updatePlayerPositions(playerScore);
			} else {
				playerScore.setBatting(Constant.N);
				playerScore.setInField(Constant.N);
				playerScore.setStrike(Constant.N);
			}
		}
		playerScoreRepo.saveAll(playerScoreList);
	}

	/**
	 * 
	 * @param playerScore
	 * This method update the positions of players in field.
	 */
	private void updatePlayerPositions(PlayerScore playerScore) {

		if (playerScore.getBattingPosition() == 1) {
			playerScore.setInField(Constant.Y);
			playerScore.setStrike(Constant.Y);
		} else if (playerScore.getBattingPosition() == 2) {
			playerScore.setInField(Constant.Y);
			playerScore.setStrike(Constant.N);
		} else {
			playerScore.setInField(Constant.N);
			playerScore.setStrike(Constant.N);
		}
	}
}
