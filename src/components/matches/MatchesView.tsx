import React, { useEffect, useState } from 'react';
import prediction from '../../app/services/prediction/PredictionService';
import IpcMatchStatsService from '../../app/services/stats/IpcMatchStatsService';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import { MatchList, MatchListEntry } from './MatchList';

export const MatchesView = () => {
  const [matches, setMatches] = useState<MatchListEntry[]>([]);

  useEffect(() => {
    const mss = new IpcMatchStatsService();
    // eslint-disable-next-line promise/always-return
    mss
      .matchesByDay(new Date(2021, 11, 26))
      // eslint-disable-next-line promise/always-return
      .then((n) => {
        const r = n.map((ms) => {
          const predictionNumber = prediction({
            bet: Bet.OVER_ZERO_FIVE,
            match: ms,
            leagueStats: undefined,
            teamStats: undefined,
          });

          const mle: MatchListEntry = {
            awayTeam: ms['Away Team'],
            homeTeam: ms['Home Team'],
            betPredictions: [{ betName: 'Over', prediction: predictionNumber }],
          };

          return mle;
        });
        setMatches(r);
      })
      .catch((reason) => console.log(reason));
  }, []);

  return (
    <>
      <MatchList entries={matches} />
    </>
  );
};

export default MatchesView;
