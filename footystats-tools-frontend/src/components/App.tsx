import React from "react";
import { Navigate, Route, Routes } from "react-router";
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
import LoadingOverlayStore from "../mobx/LoadingOverlayStore";
import { SpinnerWithMessage } from "./spinner/SpinnerWithMessage";
import { ObsJobProgressBar } from "./progress/JobProgressBar";
import "react-datetime-picker/dist/DateTimePicker.css";
import "react-calendar/dist/Calendar.css";
import "react-clock/dist/Clock.css";

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
						<Route
							path="/"
							element={<Navigate replace to={"/matchList"} />}
						/>
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
					</Routes>
				</>
			</ObsLoadingOverlay>

			<ObsJobProgressBar />
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
