import type { StorybookConfig } from "@storybook/react-webpack5";
//import '../src/assets/scss/App.scss';

const config: StorybookConfig = {
	stories: ["../src/**/*.mdx", "../src/**/*.stories.@(js|jsx|ts|tsx)"],
	addons: [
		"storybook-addon-mock",
		"@storybook/addon-links",
		"@storybook/addon-essentials",
		"@storybook/addon-interactions",
		"@storybook/preset-scss",
		{
			name: "@storybook/addon-styling",
			options: {
				sass: {
					// Require your Sass preprocessor here
					implementation: require("sass"),
				},
			},
		},
	],
	framework: {
		name: "@storybook/react-webpack5",
		options: {},
	},
	docs: {
		autodocs: "tag",
	},
};
export default config;
