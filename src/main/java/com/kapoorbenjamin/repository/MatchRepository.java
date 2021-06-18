package com.kapoorbenjamin.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.kapoorbenjamin.model.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {

	List<Match> getByHomeTeamOrAwayTeamOrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

	// permet d'indiquer le nombre d'item affichable dans la page, ici page 0, 4 items
	default List<Match> findLatestMatchesByTeam(String teamName, int count) {
		return getByHomeTeamOrAwayTeamOrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
	}
}
