package com.kapoorbenjamin.data;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.kapoorbenjamin.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

	private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

	@Override
	public Match process(final MatchInput matchInput) throws Exception {

		Match match = new Match();
//		UUID uniqueKey = UUID.randomUUID();
//
//		match.setId(uniqueKey.toString());
		match.setId(matchInput.getId());
		match.setDate(LocalDate.parse(matchInput.getDate()));
		match.setHomeTeam(matchInput.getHome_team());
		match.setAwayTeam(matchInput.getAway_team());
		match.setHomeScore(matchInput.getHome_score());
		match.setAwayScore(matchInput.getAway_score());
		match.setTournament(matchInput.getTournament());
		match.setCity(matchInput.getCity());
		match.setCountry(matchInput.getCountry());
		match.setNeutral(matchInput.getNeutral());

		// Récupération du gagnant selon le score, draw si égalité, si NA retourne NA
		if (!"NA".equals(matchInput.getAway_score()) && !"NA".equals(matchInput.getHome_score())) {
			if (Integer.parseInt(matchInput.getHome_score()) > Integer.parseInt(matchInput.getAway_score())) {
				match.setTeamWinner(matchInput.getHome_team());
			}

			if (Integer.parseInt(matchInput.getHome_score()) < Integer.parseInt(matchInput.getAway_score())) {
				match.setTeamWinner(matchInput.getAway_team());
			}

			if (Integer.parseInt(matchInput.getHome_score()) == Integer.parseInt(matchInput.getAway_score())) {
				match.setTeamWinner("draw");
			}

		} else {
			match.setTeamWinner("NA");
		}

		return match;
	}

}