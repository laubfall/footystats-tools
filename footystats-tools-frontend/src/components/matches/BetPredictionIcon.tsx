import React from "react";
import {
	PredictionResult,
	StatisticalResultOutcome,
} from "../../footystats-frontendapi";
import {
	BsAirplane,
	BsAirplaneFill,
	BsArrowUpLeftCircle,
	BsFillArrowDownCircleFill,
	BsFillArrowUpCircleFill,
	BsFillArrowUpLeftCircleFill,
} from "react-icons/bs";

export const BetPredictionIcon = (props: BetPredictionIconProps) => {
	const prediction = props.predictionResult;
	const statisticalOutcome = props.statisticalResultOutcome;

	let b10 = statisticalOutcome?.ranking?.best10Percent;
	if (statisticalOutcome != undefined && !b10) {
		// find a influencer with a best10Percent
		const influencers =
			statisticalOutcome.influencerStatisticalResultOutcomes;
		if (influencers) {
			for (const element of influencers) {
				if (element.ranking?.best10Percent) {
					b10 = true;
					break;
				}
			}
		}
	}

	let b20 = statisticalOutcome?.ranking?.best20Percent;
	if (statisticalOutcome != undefined && !b20) {
		// find a influencer with a best10Percent
		const influencers =
			statisticalOutcome.influencerStatisticalResultOutcomes;
		if (influencers) {
			for (const element of influencers) {
				if (element.ranking?.best20Percent) {
					b20 = true;
					break;
				}
			}
		}
	}

	let BetSuggestionIcon: React.JSX.Element;
	if (prediction.betOnThis) {
		if (b10) {
			BetSuggestionIcon = <BsAirplaneFill />;
		} else if (b20) {
			BetSuggestionIcon = <BsFillArrowUpCircleFill />;
		} else {
			BetSuggestionIcon = <BsFillArrowUpLeftCircleFill />;
		}
	} else if (!prediction.betOnThis) {
		if (b10) {
			BetSuggestionIcon = <BsAirplane />;
		} else if (b20) {
			BetSuggestionIcon = <BsArrowUpLeftCircle />;
		} else {
			BetSuggestionIcon = <BsFillArrowDownCircleFill />;
		}
	}

	return <>{BetSuggestionIcon}</>;
};

export type BetPredictionIconProps = {
	predictionResult: PredictionResult;
	statisticalResultOutcome: StatisticalResultOutcome;
};
