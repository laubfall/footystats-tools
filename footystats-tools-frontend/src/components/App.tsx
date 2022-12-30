import React from "react";
import { Route, Routes } from "react-router";
import { BrowserRouter } from "react-router-dom";
import { MatchesView } from "./matches/MatchesView";
import { Menu } from "./Menu";
import { PredictionQualityView } from "./predictionQuality/PredictionQualityView";
import { UploadMatchStatsView } from "./matchstats/UploadMatchStatsView";
import AlertMessagesStore from "../mobx/AlertMessages";
import { observer } from "mobx-react-lite";
import { Messages } from "./alert/Messages";
import { SettingsView } from "./settings/SettingsView";

const FootyStatsTools = () => {
	const ObsMessages = observer(() => (
		<Messages messages={[...AlertMessagesStore.messages]} />
	));

	return (
		<>
			<ObsMessages />
			<Menu />
			<Routes>
				<Route path="/">
					<Route path="matchList" element={<MatchesView />} />
					<Route
						path="predictionQuality"
						element={<PredictionQualityView />}
					/>
					<Route
						path="uploadmatchstats"
						element={<UploadMatchStatsView />}
					/>
					<Route path={"settings"} element={<SettingsView />} />
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

export default App as typeof App;
