import { uniq } from 'lodash';
import { BetPredictionDistribution } from '../../app/services/prediction/PredictionQualityService.types';

export function collectInfluencerNames(
	measurement?: BetPredictionDistribution
): string[] {
	if (!measurement) {
		return [];
	}

	let names: string[] = [];
	measurement.forEach((m) => {
		const uniqueNames = uniq(
			m.influencerDistribution.map((id) => id.influencerName)
		);
		names.push(...uniqueNames);
		names = uniq(names);
	});

	return names;
}

export default { collectInfluencerNames };
