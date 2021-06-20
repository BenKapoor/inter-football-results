package com.kapoorbenjamin.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kapoorbenjamin.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final EntityManager em;

//	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(EntityManager em) {
		this.em = em;
	}

//	@Autowired
//	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//		this.jdbcTemplate = jdbcTemplate;
//	}

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

//			jdbcTemplate.query("SELECT home_team, away_team, date, team_winner, home_score,  away_score FROM match",
//					(rs, row) -> "Home team " + rs.getString(1) + " Away team " + rs.getString(2) + " Date "
//							+ rs.getString(3) + " Winner " + rs.getString(4) + " Score " + rs.getString(5) + " - "
//							+ rs.getString(6))
//					.forEach(str -> System.out.println(str));

			Map<String, Team> teamData = new HashMap<>();

			// on type pour éviter les erreurs
			em.createQuery("select m.homeTeam, count(*) from Match m group by m.homeTeam", Object[].class)
					.getResultList().stream().map(e -> new Team((String) e[0], (long) e[1])) // recupération du nom de
																								// la team (cast string)
																								// et du nombre (long)
					.forEach(team -> teamData.put(team.getTeamName(), team));

			em.createQuery("select m.awayTeam, count(*) from Match m group by m.awayTeam", Object[].class)
					.getResultList().stream().forEach(e -> {
						Team team = teamData.get(e[0]);
						if (team != null) {
							team.setTotalMatches(team.getTotalMatches() + (long) e[1]); // rajoute le resultat du nb de
																						// matchs des equipes ext aux à
																						// dom
						}
					});

			em.createQuery("select m.teamWinner, count(*) from Match m group by teamWinner", Object[].class)
					.getResultList().stream().forEach(e -> {
						Team team = teamData.get(e[0]);
						if (team != null) {
							team.setTotalWins((long) e[1]);
						}
					});

			em.createQuery("select m.homeTeam, count(*) from Match m where m.homeScore=m.awayScore group by homeTeam",
					Object[].class).getResultList().stream().forEach(e -> {
						Team team = teamData.get(e[0]);
						if (team != null) {
							team.setTotalDraw((long) e[1]);
						}
					});

			em.createQuery("select m.awayTeam, count(*) from Match m where m.homeScore=m.awayScore group by awayTeam",
					Object[].class).getResultList().stream().forEach(e -> {
						Team team = teamData.get(e[0]);
						if (team != null) {
							team.setTotalDraw(team.getTotalDraw() + (long) e[1]);
						}
					});

			teamData.values().forEach(team -> em.persist(team));
			teamData.values().forEach(team -> System.out.println(team));
		}
	}
}