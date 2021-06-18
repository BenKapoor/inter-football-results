import { Link } from 'react-router-dom';
import { React } from 'react';

export const MatchSmallCard = ({teamName, match}) => {
    if(!match) return null;
    const otherTeam = match.homeTeam === teamName ? match.awayTeam : match.homeTeam;
    const otherTeamRoute = `/teams/${otherTeam}`;
    return (
        <div className="MatchSmallCard">
            <h3>VS <Link to={otherTeamRoute} >{otherTeam}</Link></h3>
            <p>{match.teamWinner} won by {match.homeScore} - {match.awayScore}</p>
        </div>
    );
}
