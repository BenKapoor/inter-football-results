import './MatchSmallCard.scss'

import { Link } from 'react-router-dom';
import { React } from 'react';

export const MatchSmallCard = ({teamName, match}) => {
    if(!match) return null;
    const otherTeam = match.homeTeam === teamName ? match.awayTeam : match.homeTeam;
    const otherTeamRoute = `/teams/${otherTeam}`;

    var styleCardWinner = function name() {
        return match.teamWinner === 'NA' ? 'MatchSmallCard na-card'
            : match.homeScore === match.awayScore ? 'MatchDetailCard draw-card'
            : match.teamWinner === teamName ? 'MatchSmallCard won-card'
            : 'MatchSmallCard lost-card'
    }

    return (
        <div className={styleCardWinner() }>
            <h3>VS <Link to={otherTeamRoute} >{otherTeam}</Link></h3>
            <p className="match-result">{match.teamWinner} won by {match.homeScore} - {match.awayScore}</p>
        </div>
    );
}
