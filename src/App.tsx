import React, { useState } from 'react';
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

const FootyStatsTools = () => {
  const [alertMsg, setMsg] = useState<string>();

  const history = useNavigate();

  subscribeMsgInvalidConfiguration(() => {
    history('configuration');
  });

  subscribeMsgSimpleMessage((msg) => setMsg(msg.channel));

  return (
    <>
      {alertMsg && <Alert variant="info">{alertMsg}</Alert>}
      <Menu />
      <Routes>
        <Route path="/">
          <Route path="configuration" element={<ConfigurationView />} />
          <Route index path="matchList" element={<MatchesView />} />
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
