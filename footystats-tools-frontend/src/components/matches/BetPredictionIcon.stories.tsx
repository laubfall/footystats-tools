import type { Meta, StoryObj } from "@storybook/react";
import { BetPredictionIcon } from "./BetPredictionIcon";

const meta: Meta<typeof BetPredictionIcon> = {
	component: BetPredictionIcon,
};

export default meta;
type Story = StoryObj<typeof BetPredictionIcon>;

export const Primary: Story = {
	args: {
		predictionResult: {
			betOnThis: true,
			betSuccessInPercent: 40,
			analyzeResult: "SUCCESS",
		},
		statisticalResultOutcome: {
			ranking: {
				best10Percent: true,
				best20Percent: false,
			},
		},
	},
};
