import React, { useEffect, useState } from 'react';
import IpcMatchStatsService from '../../app/services/stats/IpcMatchStatsService';
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
          const mle: MatchListEntry = {
            awayTeam: ms['Away Team'],
            homeTeam: ms['Home Team'],
            betPredictions: [],
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
