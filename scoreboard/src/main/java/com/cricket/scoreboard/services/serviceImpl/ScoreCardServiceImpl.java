package com.cricket.scoreboard.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricket.scoreboard.constants.Constant;
import com.cricket.scoreboard.constants.Score;
import com.cricket.scoreboard.entities.CricketMatch;
import com.cricket.scoreboard.entities.PlayerScore;
import com.cricket.scoreboard.entities.Team;
import com.cricket.scoreboard.models.PlayerScoreResult;
import com.cricket.scoreboard.models.PlayerScoreWrapper;
import com.cricket.scoreboard.models.Result;
import com.cricket.scoreboard.models.ScoreCard;
import com.cricket.scoreboard.models.ScoresRequest;
import com.cricket.scoreboard.repositories.CricketMatchRepo;
import com.cricket.scoreboard.repositories.PlayerScoreRepo;
import com.cricket.scoreboard.repositories.TeamRepo;
import com.cricket.scoreboard.services.ScoreCardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ScoreCardServiceImpl implements ScoreCardService {

	@Autowired
	private CricketMatchRepo cricketMatchRepo;

	@Autowired
	private TeamRepo teamRepo;

	@Autowired
	private PlayerScoreRepo playerScoreRepo;

	/**
	 * @param scores 
	 * This method updates all the data related to players in field
	 * @return scoreCard -Details of players score after an over
	 */
	@Override
	public ScoreCard getScoreCard(ScoresRequest scoresRequest) {

		List<String> scores = scoresRequest.getScores();
		List<PlayerScore> playersScoreList = playerScoreRepo.findAll();
		PlayerScoreWrapper playerScore = getCurrentPlayersInField(playersScoreList);
		Optional<Team> team = teamRepo.findById(playerScore.getPlayerOnStrike().getTeamId());
		Team teamPlaying = null;
		if (team.isPresent()) {
			teamPlaying = team.get();
		}
		int wickets = 0;
		for (String score : scores) {
			switch (score) {
			case Score.ONE: {
				updateScore(playerScore, true, 1);
				break;
			}
			case Score.TWO: {
				updateScore(playerScore, false, 2);
				break;
			}
			case Score.THREE: {
				updateScore(playerScore, true, 3);
				break;
			}
			case Score.FOUR: {
				updateScore(playerScore, false, 4);
				break;
			}
			case Score.SIX: {
				updateScore(playerScore, false, 6);
				break;
			}
			case Score.WIDE: {
				playerScore.setTotalScore(playerScore.getTotalScore() + 1);
				break;
			}
			case Score.WICKET: {
				updateWicketDetails(playerScore);
				wickets++;
				setPlayerOnStrike(playerScore, playersScoreList);
				if (teamPlaying.getNumberOfPlayers() - 1 == wickets) {
					break;
				}
				break;
			}
			}
		}
		swapPositionInField(playerScore);
		// updating team score
		teamPlaying.setOvers(teamPlaying.getOvers() + 1);
		teamPlaying.setTotalScore(teamPlaying.getTotalScore() + playerScore.getTotalScore());
		teamPlaying.setWickets(teamPlaying.getWickets() + wickets);
		teamRepo.save(teamPlaying);
		// updating playerScore
		playerScoreRepo.save(playerScore.getPlayerOnStrike());
		playerScoreRepo.save(playerScore.getPlayerOnField());
		// updating ScoreCard
		ScoreCard scoreCard = updateScoreCardDetails(playersScoreList, teamPlaying, scores.size());
		return scoreCard;
	}

	/**
	 * 
	 * @param playersScoreList 
	 * This method fetches the details of two players in the field
	 */
	private PlayerScoreWrapper getCurrentPlayersInField(List<PlayerScore> playersScoreList) {

		PlayerScore playerOnStrike = new PlayerScore();
		PlayerScore playerOnField = new PlayerScore();
		for (PlayerScore player : playersScoreList) {
			if (player.getStrike().equals(Constant.Y)) {
				playerOnStrike = player;
			}
			if (player.getInField().equals(Constant.Y) && player.getStrike().equals(Constant.N)) {
				playerOnField = player;
			}
		}
		int totalScore = 0;
		PlayerScoreWrapper playerScore = new PlayerScoreWrapper();
		playerScore.setPlayerOnField(playerOnField);
		playerScore.setPlayerOnStrike(playerOnStrike);
		playerScore.setTotalScore(totalScore);
		return playerScore;
	}

	/**
	 * @param playerScore
	 * @param swap
	 * @param score       
	 * This method update the score of the player in strike
	 */
	private void updateScore(PlayerScoreWrapper playerScore, boolean swap, int score) {

		playerScore.getPlayerOnStrike().setBalls(playerScore.getPlayerOnStrike().getBalls() + 1);
		playerScore.getPlayerOnStrike().setScore(playerScore.getPlayerOnStrike().getScore() + score);
		playerScore.setTotalScore(playerScore.getTotalScore() + score);
		if (swap) {
			swapPositionInField(playerScore);
		}
		if (score == 4) {
			playerScore.getPlayerOnStrike().setFours(playerScore.getPlayerOnStrike().getFours() + 1);
		}
		if (score == 6) {
			playerScore.getPlayerOnStrike().setSixes(playerScore.getPlayerOnStrike().getSixes() + 1);
		}
	}
	/**
	 * @param playerScore 
	 * This method update the details when player gets out.
	 */
	private void updateWicketDetails(PlayerScoreWrapper playerScore) {
		playerScore.getPlayerOnStrike().setBalls(playerScore.getPlayerOnStrike().getBalls() + 1);
		playerScore.getPlayerOnStrike().setInField(Constant.N);
		playerScore.getPlayerOnStrike().setStrike(Constant.N);
	}

	/**
	 * @param playerScore 
	 * This method swaps the position of two batsman
	 */
	private void swapPositionInField(PlayerScoreWrapper playerScore) {

		PlayerScore temp = null;
		playerScore.getPlayerOnStrike().setStrike(Constant.N);
		temp = playerScore.getPlayerOnField();
		playerScore.setPlayerOnField(playerScore.getPlayerOnStrike());
		playerScore.setPlayerOnStrike(temp);
		playerScore.getPlayerOnStrike().setStrike(Constant.Y);
	}

	/**
	 * @param playerScore
	 * @param playersScoreList 
	 * This method set new player on strike when other players gets out
	 */
	private void setPlayerOnStrike(PlayerScoreWrapper playerScore, List<PlayerScore> playersScoreList) {

		int position;
		if (playerScore.getPlayerOnStrike().getBattingPosition() > playerScore.getPlayerOnField()
				.getBattingPosition()) {
			position = playerScore.getPlayerOnStrike().getBattingPosition() + 1;
		} else {
			position = playerScore.getPlayerOnField().getBattingPosition() + 1;
		}

		int teamId = playerScore.getPlayerOnStrike().getTeamId();
		playerScoreRepo.save(playerScore.getPlayerOnStrike());
		for (PlayerScore player : playersScoreList) {
			if ((player.getBattingPosition() == position) && (player.getTeamId() == teamId)) {
				player.setInField(Constant.Y);
				player.setStrike(Constant.Y);
				playerScore.setPlayerOnStrike(player);
			}
		}
	}
	/**
	 * @param playersScoreList
	 * @param teamPlaying
	 * @param balls            
	 * This method update scoreCard after each over
	 */
	private ScoreCard updateScoreCardDetails(List<PlayerScore> playersScoreList, Team teamPlaying, int balls) {

		List<PlayerScoreResult> battingPlayers = new ArrayList<>();
		for (PlayerScore player : playersScoreList) {
			PlayerScoreResult playerScoreResult = new PlayerScoreResult();
			if (player.getBatting().equals(Constant.Y)) {
				playerScoreResult.setPlayerName(player.getPlayerName());
				playerScoreResult.setBalls(player.getBalls());
				playerScoreResult.setFours(player.getFours());
				playerScoreResult.setSixes(player.getSixes());
				playerScoreResult.setScore(player.getScore());
				battingPlayers.add(playerScoreResult);
			}
		}
		if(balls >6) {
			balls = 6;
		}
		double overs = (teamPlaying.getOvers() - 1) + (0.1 * (balls % 6)) + (balls / 6);
		log.info("Overs: {}", overs);
		ScoreCard scoreCard = new ScoreCard();
		scoreCard.setOvers(overs);
		scoreCard.setPlayerScoreList(battingPlayers);
		scoreCard.setTotalScore(teamPlaying.getTotalScore());
		scoreCard.setWickets(teamPlaying.getWickets());
		log.info("ScoreCard: {}",scoreCard);
		return scoreCard;
	}

	/**
	 * This method fetches the result of the match
	 */
	@Override
	public Result getResult() {
		Result result = new Result();
		List<CricketMatch> match = cricketMatchRepo.findAll();
		List<Team> teamsList = teamRepo.findAll();
		Team team1 = teamsList.get(0);
		Team team2 = teamsList.get(1);
		if (team1.getTotalScore() > team2.getTotalScore()) {
			result.setTeamName(team1.getTeamName());
			result.setRuns(team1.getTotalScore() - team2.getTotalScore());
			match.get(0).setWinner(team1.getTeamName());
		} else if (team1.getTotalScore() < team2.getTotalScore()) {
			result.setTeamName(team2.getTeamName());
			result.setRuns(team2.getTotalScore() - team1.getTotalScore());
			match.get(0).setWinner(team2.getTeamName());
		} else {
			result.setTeamName(Constant.DRAW);
			result.setRuns(0);
			match.get(0).setWinner(Constant.DRAW);
		}
		log.info("Match Details: {}", match);
		cricketMatchRepo.saveAll(match);
		return result;
	}

}
