package com.kapoorbenjamin.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kapoorbenjamin.model.Match;
import com.kapoorbenjamin.model.Team;
import com.kapoorbenjamin.repository.MatchRepository;
import com.kapoorbenjamin.repository.TeamRepository;

@CrossOrigin
@RestController
public class TeamController {

	private TeamRepository teamRepository;
	private MatchRepository matchRepository;

	@Autowired
	public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
		super();
		this.teamRepository = teamRepository;
		this.matchRepository = matchRepository;
	}

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = this.teamRepository.findByTeamName(teamName);

		team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4)); // 4 derniers matchs

		return team;
	}

	@GetMapping("/team/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year + 1, 1, 1);

		return this.matchRepository.getMatchesByTeamBetweenDate(
				teamName,
				startDate,
				endDate);
	}

}
