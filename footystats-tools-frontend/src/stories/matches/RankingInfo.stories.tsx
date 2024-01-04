import { RankingInfo } from "../../components/matches/RankingInfo";
import { Meta, StoryObj } from "@storybook/react";

const meta: Meta<typeof RankingInfo> = {
	component: RankingInfo,
};

export default meta;
type Story = StoryObj<typeof RankingInfo>;

export const Bet10Percent: Story = {
	args: {
		ranking: {
			best10Percent: true,
			best20Percent: true,
			position: 1,
			total: 10,
		},
	},
};

export const Bet20Percent: Story = {
	args: {
		ranking: {
			best10Percent: false,
			best20Percent: true,
			position: 1,
			total: 10,
		},
	},
};
