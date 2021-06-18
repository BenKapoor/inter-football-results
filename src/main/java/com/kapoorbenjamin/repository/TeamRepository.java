package com.kapoorbenjamin.repository;

import org.springframework.data.repository.CrudRepository;

import com.kapoorbenjamin.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

	Team findByTeamName(String teamName);
}
