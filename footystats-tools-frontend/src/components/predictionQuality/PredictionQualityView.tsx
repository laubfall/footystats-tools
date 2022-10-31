import React, { useContext, useEffect, useState } from "react";
import { Accordion, AccordionContext, Button, Col, Row } from "react-bootstrap";
import AccordionBody from "react-bootstrap/AccordionBody";
import AccordionHeader from "react-bootstrap/AccordionHeader";
import translate from "../../i18n/translate";
import IpcPredictionQualityService from "../../app/services/prediction/IpcPredictionQualityService";
import { ReportList } from "./ReportList";
import { PredictionGraphView } from "./PredictionGraphView";
import { InfluencerDistributionGraphView } from "./InfluencerDistributionGraph";
import { InfluencerPredictionGraphView } from "./InfluencerPredictionGraphView";
import {
	BetPredictionQuality,
	BetPredictionQualityBetEnum,
	PredictionQualityReport,
} from "../../footystats-frontendapi";
import { NO_REVISION_SO_FAR } from "../../app/services/prediction/PredictionQualityService.types";

export const PredictionQualityView = () => {
	const [report, setReport] = useState<PredictionQualityReport>();

	const [currentMeasurement, setCurrentMeasurement] =
		useState<BetPredictionQuality>();

	const [recalculateAvailable, setRecalculateAvailable] = useState(false);

	const predictionQualityService = new IpcPredictionQualityService();

	useEffect(() => {
		predictionQualityService
			.latestReport()
			.then((rep) => setReport(rep))
			.catch((reason) =>
				console.error("Failed to get latest report", reason),
			);

		predictionQualityService
			.latestRevision()
			.then((rev) =>
				setRecalculateAvailable(rev.revision !== NO_REVISION_SO_FAR),
			)
			.catch((reason) =>
				console.error(
					"Failed to compute state for recalculate button",
					reason,
				),
			);
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
							<InfluencerDistributionGraphView
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
							<InfluencerDistributionGraphView
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

	return (
		<>
			<Row>
				<Col>
					<Button
						onClick={async () => {
							setReport(
								await predictionQualityService.computeQuality(),
							);
						}}
					>
						{translate(
							"renderer.predictionqualityview.button.calculate",
						)}
					</Button>
					<Button
						disabled={recalculateAvailable === false}
						onClick={async () => {
							const lr =
								await predictionQualityService.latestRevision();
							setReport(
								await predictionQualityService.recomputeQuality(
									lr,
								),
							);
						}}
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
