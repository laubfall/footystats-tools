import { ipcRenderer } from 'electron';
import React from 'react';
import { Route, Routes, useNavigate } from 'react-router';
import { HashRouter } from 'react-router-dom';
import { ConfigurationView } from './components/configuration/ConfigurationView';
import { MatchList } from './components/matches/MatchList';
import { Menu } from './components/Menu';

const FootyStatsTools = () => {
  const matchListEntries = [
    {
      awayTeam: 'team away',
      homeTeam: 'team home',
      betPredictions: [{ betName: 'over1.5', prediction: 40 }],
    },
  ];

  const history = useNavigate();

  ipcRenderer.on('on-config', (event) => {
    history('configuration');
  });

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

// eslint-disable-next-line import/prefer-default-export
export const App = () => {
  return (
    <>
      <HashRouter>
        <FootyStatsTools />
      </HashRouter>
    </>
  );
};
