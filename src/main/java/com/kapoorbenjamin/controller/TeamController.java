package com.kapoorbenjamin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kapoorbenjamin.model.Team;
import com.kapoorbenjamin.repository.MatchRepository;
import com.kapoorbenjamin.repository.TeamRepository;

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

}
