import React, { useEffect } from 'react';
import { Route, Routes } from 'react-router';
import { ConfigurationView } from './components/configuration/ConfigurationView';
import { MatchList } from './components/matches/MatchList';
import { Menu } from './components/Menu';

// eslint-disable-next-line import/prefer-default-export
export const App = () => {
  const matchListEntries = [
    {
      awayTeam: 'team away',
      homeTeam: 'team home',
      betPredictions: [{ betName: 'over1.5', prediction: 40 }],
    },
  ];

  return (
    <>
      <Menu />
      <Routes>
        <Route path="/">
          <Route path="configuration" element={<ConfigurationView />} />
          <Route
            index
            path="matchList"
            element={<MatchList entries={matchListEntries} />}
          />
        </Route>
      </Routes>
    </>
  );
};
