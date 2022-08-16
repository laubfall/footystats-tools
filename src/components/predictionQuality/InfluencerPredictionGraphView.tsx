import React from 'react';
import { union, uniqueId } from 'lodash';
import { Alert } from 'react-bootstrap';
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

export type InfluencerPredictionGraphProps = {
	i1Distribution: PercentDistribution[];
	i2Distribution: PercentDistribution[];
	title: string;
};

export type InfluencerPredictionGraphViewProps = {
	measurement: BetPredictionQuality;
};

const InfluencerPredictionGraph = ({
	i1Distribution,
	i2Distribution,
	title,
}: InfluencerPredictionGraphProps) => {
	if (i1Distribution.length === 0 && i2Distribution.length === 0) {
		return (
			<>
				<Alert variant="info">
					{translate(
						'renderer.predictionqualitiyview.influencerpredictionquality.nodata',
						[title]
					)}
				</Alert>
			</>
		);
	}

	return (
		<>
			<h3>{title}</h3>
			<PercentageDistributionGraph
				graphs={[
					{ name: 'i1Distribution', data: i1Distribution },
					{ name: 'i2Distribution', data: i2Distribution },
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
						title={name}
						key={uniqueId()}
					/>
				);
			})}
		</>
	);
};

export default { InfluencerPredictionGraphView };
