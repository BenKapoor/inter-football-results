import './TeamPage.scss';

import { React, useEffect, useState } from 'react';

import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';
import { PieChart } from 'react-minimal-pie-chart';
import { useParams } from 'react-router-dom';

export const TeamPage = () => {

    const [team, setTeam] = useState({matches: []});
    const { teamName } = useParams();

    useEffect(
        () => {
            const fetchMatches = async () => {
                const response = await fetch(`http://localhost:8080/team/${teamName}`);
                const data = await response.json();
                setTeam(data);
            };

            fetchMatches();
        }, [teamName]
    );

    // gère si le nom de l'équipe d'existe pas
    if (!team ||!team.teamName){
        return <h1>Team not found</h1>
    }
    
    return (
        <div className="TeamPage">
            <div className="team-name-section">
                <h1 className="team-name">{team.teamName}</h1>
            </div>
            <div className="win-loss-section">
                Wins / Losses
                <PieChart
                    data={[
                        { title: 'Win', value: team.totalWins, color: '#4da375' },
                        { title: 'Losses', value: team.totalMatches - team.totalDraw - team.totalWins, color: '#a34d5d' },
                        { title: 'Draw', value: team.totalDraw, color: '#889392' },
                    ]}
                    />
            </div>
            <div className="match-detail-section">
                <h3>Latest Matches</h3>

                <MatchDetailCard teamName={team.teamName} match={team.matches[0]}/>
            </div>
            
            {team.matches.slice(1).map(match => <MatchSmallCard teamName={team.teamName} match={match} />)}
            
            <div  className="more-link">
                <a href="#">More &gt;</a>
            </div>
        </div>
    );
}
