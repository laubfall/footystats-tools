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
import LoadingOverlay from "react-loading-overlay";
import { Spinner } from "react-bootstrap";
import LoadingOverlayStore from "../mobx/LoadingOverlayStore";
import { SpinnerWithMessage } from "./spinner/SpinnerWithMessage";

const FootyStatsTools = () => {
	const ObsMessages = observer(() => (
		<Messages messages={[...AlertMessagesStore.messages]} />
	));

	const loadingOverlayStore = LoadingOverlayStore;

	const ObsLoadingOverlay = observer(
		({
			children,
			loading = loadingOverlayStore.loading,
			message = loadingOverlayStore.loadingMessage,
		}: any) => {
			return (
				<LoadingOverlay
					spinner={<SpinnerWithMessage message={message} />}
					active={loading}
				>
					{children}
				</LoadingOverlay>
			);
		},
	);

	return (
		<>
			<ObsMessages />
			<Menu />
			<ObsLoadingOverlay>
				<>
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
							<Route
								path={"settings"}
								element={<SettingsView />}
							/>
						</Route>
					</Routes>
				</>
			</ObsLoadingOverlay>
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
