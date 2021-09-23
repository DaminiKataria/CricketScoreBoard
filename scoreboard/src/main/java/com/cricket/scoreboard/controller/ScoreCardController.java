package com.cricket.scoreboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cricket.scoreboard.constants.API;
import com.cricket.scoreboard.models.MatchDetailsRequest;
import com.cricket.scoreboard.models.Result;
import com.cricket.scoreboard.models.ScoreCard;
import com.cricket.scoreboard.models.ScoresRequest;
import com.cricket.scoreboard.services.MatchDetailsService;
import com.cricket.scoreboard.services.ScoreCardService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This controller fetches the details of the current match.
 */
@Slf4j
@RestController
@RequestMapping(API.cricket)
public class ScoreCardController {

	@Autowired
	private ScoreCardService scoreCardService;

	@Autowired
	private MatchDetailsService matchDetailsService;

	@PostMapping(API.matchDetails)
	public ResponseEntity<HttpStatus> updateMatchDetails(@RequestBody MatchDetailsRequest matchDetailsRequest) {

		try {
			matchDetailsService.updateMatchDetails(matchDetailsRequest);

		} catch (Exception exception) {
			log.error("Exception: {}", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(API.scoreCard)
	public ResponseEntity<ScoreCard> updateMatchScore(@RequestBody ScoresRequest scoresRequest) {
		ScoreCard scoreCard = null;
		try {
			scoreCard = scoreCardService.getScoreCard(scoresRequest);

		} catch (Exception exception) {
			log.error("Exception: {}", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(scoreCard, HttpStatus.OK);
	}

	@PostMapping(API.secondInnings)
	public ResponseEntity<HttpStatus> updateSecondInningsDetails() {
		try {
			matchDetailsService.updateSecondInningsDetails();

		} catch (Exception exception) {
			log.error("Exception: {}", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(API.result)
	public @ResponseBody ResponseEntity<Result> getResultDetails() {
		Result result = null;
		try {
			result = scoreCardService.getResult();
			
		} catch (Exception exception) {
			log.error("Exception: {}", exception);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
