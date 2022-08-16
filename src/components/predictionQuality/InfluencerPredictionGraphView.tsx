import React from 'react';
import { union, uniqueId } from 'lodash';
import { Alert } from 'react-bootstrap';
import { colord } from 'colord';
import {
	BetPredictionDistribution,
	BetPredictionQuality,
	PercentDistribution,
} from '../../app/services/prediction/PredictionQualityService.types';
import { PercentageDistributionGraph } from './PercentageDistributionGraph';
import { collectInfluencerNames } from './functions';
import { InfluencerName } from '../../app/services/prediction/PredictionService.types';
import translate from '../../i18n/translate';
import { PrecheckResult } from '../../app/types/prediction/BetResultInfluencer';
import { Bet } from '../../app/types/prediction/BetPredictionContext';

export type InfluencerPredictionGraphProps = {
	i1Distribution: PercentDistribution[];
	i2Distribution: PercentDistribution[];
	name: InfluencerName;
	bet: Bet;
};

export type InfluencerPredictionGraphViewProps = {
	measurement: BetPredictionQuality;
};

const InfluencerPredictionGraph = ({
	i1Distribution,
	i2Distribution,
	name,
	bet,
}: InfluencerPredictionGraphProps) => {
	if (i1Distribution.length === 0 && i2Distribution.length === 0) {
		return (
			<>
				<Alert variant="info">
					{translate(
						'renderer.predictionqualitiyview.influencerpredictionquality.nodata',
						[name]
					)}
				</Alert>
			</>
		);
	}

	return (
		<>
			<h3>{name}</h3>
			<PercentageDistributionGraph
				graphs={[
					{
						name: `${translate(
							`renderer.matchesview.bet.${Bet[bet]}`
						)} ${translate('renderer.predictiongraphview.bet')}`,
						data: i1Distribution,
						color: colord('rgb(40,200,0)').toRgb(),
					},
					{
						name: `${translate(
							`renderer.matchesview.bet.${Bet[bet]}`
						)} ${translate(
							'renderer.predictiongraphview.bet.failed'
						)}`,
						data: i2Distribution,
					},
				]}
			/>
		</>
	);
};

function influencerPercentDistributions(
	name: InfluencerName,
	distribution: BetPredictionDistribution
): PercentDistribution[] {
	const aggregatePredictionPercentAndCount: Map<number, PercentDistribution> =
		new Map();

	distribution
		.map((ppd) => ppd.influencerDistribution)
		.forEach((influencerDistributions) => {
			influencerDistributions
				.filter((infDist) => infDist.influencerName === name)
				.filter(
					(infDist) => infDist.precheckResult === PrecheckResult.OK
				)
				.forEach((infDist) => {
					const existing = aggregatePredictionPercentAndCount.get(
						infDist.predictionPercent
					);
					if (existing) {
						existing.count += infDist.count;
					} else {
						aggregatePredictionPercentAndCount.set(
							infDist.predictionPercent,
							{
								count: infDist.count,
								predictionPercent: infDist.predictionPercent,
							}
						);
					}
				});
		});

	return [...aggregatePredictionPercentAndCount.values()];
}

export const InfluencerPredictionGraphView = ({
	measurement,
}: InfluencerPredictionGraphViewProps) => {
	const n1 = collectInfluencerNames(measurement.distributionBetOnThis);
	const n2 = collectInfluencerNames(measurement.distributionBetOnThisFailed);
	const relevantInfluencerNames = union(n1, n2);

	return (
		<>
			{relevantInfluencerNames.map((name) => {
				const g1Data = influencerPercentDistributions(
					name,
					measurement.distributionBetOnThis || []
				);

				const g2Data = influencerPercentDistributions(
					name,
					measurement.distributionBetOnThisFailed || []
				);
				return (
					<InfluencerPredictionGraph
						i1Distribution={g1Data}
						i2Distribution={g2Data}
						name={name}
						bet={measurement.bet}
						key={uniqueId()}
					/>
				);
			})}
		</>
	);
};

export default { InfluencerPredictionGraphView };
