import { Link } from 'react-router-dom';
import { React } from 'react';

export const MatchDetailCard = ({teamName, match}) => {
    if(!match) return null;
    const otherTeam = match.homeTeam === teamName ? match.awayTeam : match.homeTeam;
    const otherTeamRoute = `/teams/${otherTeam}`;

    return (
        <div className="MatchDetailCard">
            <h3>Latest Matches</h3>
            <h1>VS <Link to={otherTeamRoute} >{otherTeam}</Link></h1>
            <h2>{match.date}</h2>
            <h3>at {match.city} - {match.country}</h3>
            <h3>{match.teamWinner} won by {match.homeScore} - {match.awayScore}</h3>
        </div>
    );
}
