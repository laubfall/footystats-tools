import type { Meta, StoryObj } from "@storybook/react";
import MatchTable from "../../components/liveandhot/MatchTable";
import { LiveAndHotMatchesHotBetsEnum } from "../../footystats-frontendapi";

const meta: Meta<typeof MatchTable> = {
	component: MatchTable,
};

export default meta;

type Story = StoryObj<typeof MatchTable>;

export const TableWithLiveAndHotMatches: Story = {
	args: {
		matches: [
			{
				awayTeam: "Frankfurt",
				homeTeam: "Bayern",
				country: { countryNameByFootystats: "Germany" },
				hotBets: new Set([LiveAndHotMatchesHotBetsEnum.OverOneFive]),
			},
		],
	},
};
