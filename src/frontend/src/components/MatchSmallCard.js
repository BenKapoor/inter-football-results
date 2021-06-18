import { React } from 'react';

export const MatchSmallCard = ({match}) => {
  return (
    <div className="MatchSmallCard">
        <p>{match.homeTeam} VS {match.awayTeam}</p>
    </div>
  );
}
