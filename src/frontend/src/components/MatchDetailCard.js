import './MatchDetailCard.scss';

import { Link } from 'react-router-dom';
import { React } from 'react';

export const MatchDetailCard = ({teamName, match}) => {
    if(!match) return null;
    const otherTeam = match.homeTeam === teamName ? match.awayTeam : match.homeTeam;
    const otherTeamRoute = `/teams/${otherTeam}`;

    var styleCardWinner = function name() {
        return match.teamWinner === 'NA' ? 'MatchDetailCard na-card'
            : match.homeScore === match.awayScore ? 'MatchDetailCard draw-card'
            : match.teamWinner === teamName ? 'MatchDetailCard won-card'
            : 'MatchDetailCard lost-card'
    }
    
    return (
        <div className={styleCardWinner() }>           
            <div>
                <span className="vs">vs</span>
                <h1><Link to={otherTeamRoute} >{otherTeam}</Link></h1>
                <h2 className="match-date">{match.date}</h2>
                <h3 className="match-localisation">at {match.city} - {match.country}</h3>
                <h3 className="match-result">{match.teamWinner} won by {match.homeScore} - {match.awayScore}</h3>
            </div>
            <div className="additional-detail">
                <h3>Tournament</h3>
                <p>{match.tournament}</p>
                <h3>Home Team</h3>
                <p>{match.homeTeam}</p>
                <h3>Away Team</h3>
                <p>{match.awayTeam}</p>
                <h3>Neutral venue</h3>
                <p>{match.neutral}</p>
            </div>
            
        </div>
    );

    
}
