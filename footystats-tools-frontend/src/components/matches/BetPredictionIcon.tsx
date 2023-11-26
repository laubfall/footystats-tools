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

	let BetSuggestionIcon: React.JSX.Element;
	if (prediction.betOnThis) {
		if (statisticalOutcome?.ranking?.best10Percent) {
			BetSuggestionIcon = <BsAirplaneFill />;
		} else if (statisticalOutcome?.ranking?.best20Percent) {
			BetSuggestionIcon = <BsFillArrowUpCircleFill />;
		} else {
			BetSuggestionIcon = <BsFillArrowUpLeftCircleFill />;
		}
	} else if (!prediction.betOnThis) {
		if (statisticalOutcome?.ranking?.best10Percent) {
			BetSuggestionIcon = <BsAirplane />;
		} else if (statisticalOutcome?.ranking?.best20Percent) {
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
