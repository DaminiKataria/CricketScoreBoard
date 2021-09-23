package com.cricket.scoreboard.services;

import com.cricket.scoreboard.models.Result;
import com.cricket.scoreboard.models.ScoreCard;
import com.cricket.scoreboard.models.ScoresRequest;

public interface ScoreCardService {

	ScoreCard getScoreCard(ScoresRequest scoresRequest);
	
	Result getResult();

	
}
