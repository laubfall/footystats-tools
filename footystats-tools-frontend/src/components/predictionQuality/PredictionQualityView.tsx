import React, { useContext, useEffect, useState } from "react";
import { Accordion, AccordionContext, Button, Col, Row } from "react-bootstrap";
import AccordionBody from "react-bootstrap/AccordionBody";
import AccordionHeader from "react-bootstrap/AccordionHeader";
import translate from "../../i18n/translate";
import IpcPredictionQualityService from "../../app/services/prediction/IpcPredictionQualityService";
import { ReportList } from "./ReportList";
import { PredictionGraphView } from "./PredictionGraphView";
import { InfluencerDistributionScatterChartView } from "./InfluencerDistributionScatterChart";
import { InfluencerPredictionGraphView } from "./InfluencerPredictionGraphView";
import {
	BetPredictionQuality,
	BetPredictionQualityBetEnum,
	PredictionQualityReport,
} from "../../footystats-frontendapi";
import { NO_REVISION_SO_FAR } from "../../app/services/prediction/PredictionQualityService.types";
import { apiCatchReasonHandler } from "../functions";
import AlertMessageStore from "../../mobx/AlertMessages";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";

export const PredictionQualityView = () => {
	const [report, setReport] = useState<PredictionQualityReport>();

	const [currentMeasurement, setCurrentMeasurement] =
		useState<BetPredictionQuality>();

	const [recalculateAvailable, setRecalculateAvailable] = useState(false);

	const predictionQualityService = new IpcPredictionQualityService();

	useEffect(() => {
		LoadingOverlayStore.loadingNow();
		try {
			predictionQualityService
				.latestReport()
				.then((rep) => setReport(rep))
				.catch((reason) => {
					if (reason.response?.status === 404) {
						AlertMessageStore.addMessage(
							translate(
								"renderer.predictionqualitiyview.messages.noreport",
							),
						);
						return reason;
					}
					apiCatchReasonHandler(reason);
				});

			predictionQualityService
				.latestRevision()
				.then((rev) =>
					setRecalculateAvailable(
						rev.revision !== NO_REVISION_SO_FAR,
					),
				)
				.catch(apiCatchReasonHandler);
		} finally {
			LoadingOverlayStore.notLoadingNow();
		}
	}, []);

	useEffect(() => {
		const measurement = report?.measurements.find(
			(bpc) => bpc.bet === BetPredictionQualityBetEnum.BttsYes,
		);
		setCurrentMeasurement(measurement);
	}, [report]);

	const InfluencerDistributions = () => {
		const { activeEventKey } = useContext(AccordionContext);
		return (
			<>
				{activeEventKey === "0" && (
					<Row>
						<Col>
							<InfluencerDistributionScatterChartView
								distributionBetSuccess={
									currentMeasurement?.distributionBetOnThis ||
									[]
								}
								distributionBetFailed={
									currentMeasurement?.distributionDontBetOnThis ||
									[]
								}
							/>
						</Col>

						<Col>
							<InfluencerDistributionScatterChartView
								distributionBetSuccess={
									currentMeasurement?.distributionBetOnThisFailed ||
									[]
								}
								distributionBetFailed={
									currentMeasurement?.distributionDontBetOnThisFailed ||
									[]
								}
							/>
						</Col>
					</Row>
				)}
			</>
		);
	};

	function handleOnClickComputeQuality() {
		LoadingOverlayStore.loadingNow();
		predictionQualityService
			.computeQuality()
			.then(setReport)
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}

	function handleOnClickRecomputeQuality() {
		LoadingOverlayStore.loadingNow();
		predictionQualityService
			.latestRevision()
			.then((revision) =>
				predictionQualityService
					.recomputeQuality(revision)
					.then(setReport)
					.catch(apiCatchReasonHandler),
			)
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}

	return (
		<>
			<Row>
				<Col>
					<Button onClick={handleOnClickComputeQuality}>
						{translate(
							"renderer.predictionqualityview.button.calculate",
						)}
					</Button>
					<Button
						disabled={recalculateAvailable === false}
						onClick={handleOnClickRecomputeQuality}
					>
						{translate(
							"renderer.predictionqualityview.button.recalculate",
						)}
					</Button>
				</Col>
			</Row>
			<ReportList
				report={report}
				onRowClicked={(row) => {
					const measurement = report?.measurements.find(
						(bpq) => bpq.bet === row.bet,
					);
					setCurrentMeasurement(measurement);
				}}
			/>
			<PredictionGraphView measurement={currentMeasurement} />

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
						{currentMeasurement && (
							<InfluencerPredictionGraphView
								measurement={currentMeasurement}
							/>
						)}
					</AccordionBody>
				</Accordion.Item>
				<Accordion.Item eventKey="0">
					<AccordionHeader>
						{translate(
							"renderer.predictionqualitiyview.influencerdistributiongraph",
						)}
					</AccordionHeader>
					<AccordionBody>
						<InfluencerDistributions />
					</AccordionBody>
				</Accordion.Item>
			</Accordion>
		</>
	);
};

export default { PredictionQualityView };
