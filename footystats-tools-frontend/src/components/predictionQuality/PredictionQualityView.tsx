import React, { useEffect, useState } from "react";
import { Accordion, Button } from "react-bootstrap";
import AccordionBody from "react-bootstrap/AccordionBody";
import AccordionHeader from "react-bootstrap/AccordionHeader";
import translate from "../../i18n/translate";
import IpcPredictionQualityService from "../../app/services/prediction/IpcPredictionQualityService";
import { ReportList } from "./ReportList";
import { PredictionGraphView } from "./PredictionGraphView";
import { InfluencerPredictionGraphView } from "./InfluencerPredictionGraphView";
import { apiCatchReasonHandler } from "../functions";
import AlertMessageStore from "../../mobx/AlertMessages";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";
import {
	BetPredictionQualityBetAggregate,
	Report,
} from "../../footystats-frontendapi";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";
import JobProgressStore from "../../mobx/JobProgressStore";

export const PredictionQualityView = () => {
	const [report, setReport] = useState<Report>();

	const [currentBetAggregate, setCurrentBetAggregate] =
		useState<Array<BetPredictionQualityBetAggregate>>();

	const [selectedBet, setSelectedBet] = useState<BetPredictionQualityBetEnum>(
		BetPredictionQualityBetEnum.OverZeroFive,
	);

	const predictionQualityService = new IpcPredictionQualityService();

	useEffect(() => {
		setCurrentBetAggregate(report?.betPredictionDistributions);
	}, [report]);

	useEffect(() => {
		LoadingOverlayStore.loadingNow();
		predictionQualityService
			.latestReport(selectedBet)
			.then(setReport)
			.catch((reason) => {
				if (reason.response?.status === 404) {
					AlertMessageStore.addMessage(
						translate(
							"renderer.predictionqualitiyview.messages.noreport",
						),
					);
					return reason;
				}
				return apiCatchReasonHandler(reason);
			})
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}, [selectedBet]);

	function handleOnClickComputeQuality() {
		LoadingOverlayStore.loadingNow();
		predictionQualityService
			.computeQuality()
			.then((jobInformation) => {
				JobProgressStore.addJob(jobInformation);
				predictionQualityService
					.latestReport(selectedBet)
					.then(setReport);
			})
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}

	function handleOnClickRecomputeQuality() {
		LoadingOverlayStore.loadingNow();
		predictionQualityService
			.recomputeQuality()
			.then((jobInformation) => {
				JobProgressStore.addJob(jobInformation);
				predictionQualityService
					.latestReport(selectedBet)
					.then(setReport);
			})
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}

	return (
		<>
			<ReportList
				report={report}
				onRowClicked={(row) => setSelectedBet(row.bet)}
				selectedBet={selectedBet}
			/>
			<hr />
			<h3>
				{translate(
					"renderer.predictionqualitiyview.header.betprediction",
				)}
			</h3>

			<PredictionGraphView
				betPredictionPercentDistribution={currentBetAggregate}
				bet={selectedBet}
			/>

			<Accordion
				title={translate(
					"renderer.predictionqualitiyview.influencerdistributiongraph",
				)}
			>
				<Accordion.Item eventKey="1">
					<AccordionHeader>
						{translate(
							"renderer.predictionqualitiyview.influencerpredictionquality",
						)}
					</AccordionHeader>
					<AccordionBody>
						{currentBetAggregate && (
							<InfluencerPredictionGraphView
								measurementBetInfluencerAggregate={
									report?.betInfluencerPercentDistributions
								}
								bet={selectedBet}
							/>
						)}
					</AccordionBody>
				</Accordion.Item>
			</Accordion>
			<div className={"m-2 d-flex justify-content-end"}>
				<div className={"me-2"}>
					<Button onClick={handleOnClickComputeQuality}>
						{translate(
							"renderer.predictionqualityview.button.calculate",
						)}
					</Button>
				</div>
				<div>
					<Button onClick={handleOnClickRecomputeQuality}>
						{translate(
							"renderer.predictionqualityview.button.recalculate",
						)}
					</Button>
				</div>
			</div>
		</>
	);
};

export default { PredictionQualityView };
