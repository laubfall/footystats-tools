import React, { useEffect, useState } from "react";
import {
	LiveAndHotControllerApi,
	LiveAndHotMatches,
} from "../../footystats-frontendapi";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";
import { apiCatchReasonHandler } from "../functions";
import NoLiveAndHotMatches from "./NoLiveAndHotMatches";
import MatchTable from "./MatchTable";
import { Alert, Col, Container, ProgressBar, Row } from "react-bootstrap";
import translate from "../../i18n/translate";
import { useTimer } from "react-timer-hook";

export const LiveAndHotView = () => {
	const refreshAfterSeconds = 60;

	let startTime = new Date();

	const [liveAndHot, setLiveAndHot] = useState<LiveAndHotMatches[]>([]);

	const [refresh, setRefresh] = useState(false);

	const [expired, setExpired] = useState(false);

	useEffect(() => {
		const api = new LiveAndHotControllerApi();
		LoadingOverlayStore.loadingNow();
		api.list()
			.then(setLiveAndHot)
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}, [refresh]);

	const { totalSeconds, restart } = useTimer({
		expiryTimestamp: startTime,
		onExpire: () => setExpired(!expired),
	});

	useEffect(() => {
		setRefresh(!refresh);
		startTime = new Date();
		startTime.setSeconds(startTime.getSeconds() + refreshAfterSeconds);
		restart(startTime, true);
	}, [expired]);

	const RefreshBorder = ({ children }: { children: React.ReactNode }) => (
		<Container className={"m-4"}>
			{children}
			<Row>
				<Col>
					<ProgressBar
						now={totalSeconds}
						label={translate("renderer.liveandhot.view.refresh", [
							totalSeconds.toString(),
						])}
						max={refreshAfterSeconds}
					/>
				</Col>
			</Row>
		</Container>
	);

	if (liveAndHot?.length === 0) {
		return (
			<RefreshBorder>
				<NoLiveAndHotMatches />
			</RefreshBorder>
		);
	}

	return (
		<RefreshBorder>
			<Alert variant="success">
				<Alert.Heading>
					{translate("renderer.liveandhot.view.alert.header")}
				</Alert.Heading>
				<p>{translate("renderer.liveandhot.view.alert.text")}</p>
			</Alert>
			<MatchTable matches={liveAndHot} />;
		</RefreshBorder>
	);
};

export default LiveAndHotView;
