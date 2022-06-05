import React, { useEffect, useState } from 'react';
import { Alert } from 'react-bootstrap';
import { Route, Routes, useNavigate } from 'react-router';
import { HashRouter } from 'react-router-dom';
import {
  subscribeMsgInvalidConfiguration,
  subscribeMsgSimpleMessage,
} from './app/services/application/gui/IpcMain2Renderer';
import { ConfigurationView } from './components/configuration/ConfigurationView';
import { MatchesView } from './components/matches/MatchesView';
import { Menu } from './components/Menu';
import { PredictionQualityView } from './components/predictionQuality/PredictionQualityView';

const FootyStatsTools = () => {
  const [alertMsg, setMsg] = useState<string>();

  const history = useNavigate();

  useEffect(() => {
    subscribeMsgInvalidConfiguration(() => {
      history('configuration');
    });
    subscribeMsgSimpleMessage((msg) => setMsg(msg.message));
  }, []);

  return (
    <>
      {alertMsg && <Alert variant="info">{alertMsg}</Alert>}
      <Menu />
      <Routes>
        <Route path="/">
          <Route path="configuration" element={<ConfigurationView />} />
          <Route path="matchList" element={<MatchesView />} />
          <Route path="predictionQuality" element={<PredictionQualityView />} />
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
