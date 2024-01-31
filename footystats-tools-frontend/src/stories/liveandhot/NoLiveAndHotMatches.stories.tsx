import type { Meta, StoryObj } from "@storybook/react";
import NoLiveAndHotMatches from "../../components/liveandhot/NoLiveAndHotMatches";

const meta: Meta<typeof NoLiveAndHotMatches> = {
	component: NoLiveAndHotMatches,
};

export default meta;

type Story = StoryObj<typeof NoLiveAndHotMatches>;

export const ShowNoLiveAndHotMatches: Story = {};
