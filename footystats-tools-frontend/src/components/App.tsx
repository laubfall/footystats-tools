import React, { useEffect, useState } from "react";
import { Alert } from "react-bootstrap";
import { Route, Routes, useNavigate } from "react-router";
import { BrowserRouter } from "react-router-dom";
import { MatchesView } from "./matches/MatchesView";
import { Menu } from "./Menu";
import { PredictionQualityView } from "./predictionQuality/PredictionQualityView";
import { UploadMatchStatsView } from "./matchstats/UploadMatchStatsView";

const FootyStatsTools = () => {
  const [alertMsg, setMsg] = useState<string>();

  const history = useNavigate();

  return (
    <>
      {alertMsg && <Alert variant="info">{alertMsg}</Alert>}
      <Menu />
      <Routes>
        <Route path="/">
          <Route path="matchList" element={<MatchesView />} />
          <Route path="predictionQuality" element={<PredictionQualityView />} />
          <Route path="uploadmatchstats" element={<UploadMatchStatsView />} />
        </Route>
      </Routes>
    </>
  );
};

export const App = () => {
  return (
    <>
      <BrowserRouter>
        <FootyStatsTools />
      </BrowserRouter>
    </>
  );
};

export default App as typeof App