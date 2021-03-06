import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import log from 'electron-log';
import translate from '../../i18n/translate';
import IpcPredictionQualityService from '../../app/services/prediction/IpcPredictionQualityService';
import { ReportList } from './ReportList';
import {
	BetPredictionQuality,
	NO_REVISION_SO_FAR,
	PredictionQualityReport,
} from '../../app/services/prediction/PredictionQualityService.types';
import { PredictionGraphView } from './PredictionGraphView';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import { InfluencerDistributionGraphView } from './InfluencerDistributionGraph';

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
				log.error('Failed to get latest report', reason)
			);

		predictionQualityService
			.latestRevision()
			.then((rev) => setRecalculateAvailable(rev !== NO_REVISION_SO_FAR))
			.catch((reason) =>
				log.error(
					'Failed to compute state for recalculate button',
					reason
				)
			);
	}, []);

	useEffect(() => {
		const measurement = report?.measurements.find(
			(bpc) => bpc.bet === Bet.BTTS_YES
		);
		setCurrentMeasurement(measurement);
	}, [report]);

	return (
		<>
			<Row>
				<Col>
					<Button
						onClick={async () => {
							setReport(
								await predictionQualityService.computeQuality()
							);
						}}
					>
						{translate(
							'renderer.predictionqualityview.button.calculate'
						)}
					</Button>
					<Button
						disabled={recalculateAvailable === false}
						onClick={async () => {
							const lr =
								await predictionQualityService.latestRevision();
							setReport(
								await predictionQualityService.recomputeQuality(
									lr
								)
							);
						}}
					>
						{translate(
							'renderer.predictionqualityview.button.recalculate'
						)}
					</Button>
				</Col>
			</Row>
			<ReportList
				report={report}
				onRowClicked={(row) => {
					const measurement = report?.measurements.find(
						(bpq) => bpq.bet === row.bet
					);
					setCurrentMeasurement(measurement);
				}}
			/>
			<PredictionGraphView measurement={currentMeasurement} />
			<InfluencerDistributionGraphView
				distributionBetSuccess={
					currentMeasurement?.distributionBetOnThis || []
				}
				distributionBetFailed={
					currentMeasurement?.distributionBetOnThisFailed || []
				}
			/>
		</>
	);
};

export default { PredictionQualityView };
