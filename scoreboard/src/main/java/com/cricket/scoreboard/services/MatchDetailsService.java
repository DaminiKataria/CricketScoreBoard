package com.cricket.scoreboard.services;

import com.cricket.scoreboard.models.MatchDetailsRequest;

public interface MatchDetailsService {

	void updateMatchDetails(MatchDetailsRequest matchDetailsRequest);

	void updateSecondInningsDetails();
}
