import React from "react";
import {
	CartesianGrid,
	Legend,
	Line,
	LineChart,
	Tooltip,
	XAxis,
	YAxis,
} from "recharts";
import { uniqueId } from "lodash";
import { colord, RgbColor } from "colord";

function createData(graphs: PercentageDistributionGraphData[]): Data[] {
	const data: Data[] = [];
	graphs.forEach((value, idx) => {
		value.data?.forEach((graphData) => {
			let d = data.find(
				(v) => v.predictionPercent === graphData.xPredictionPercent,
			);
			if (d === undefined) {
				d = {
					predictionPercent: graphData.xPredictionPercent,
					y0: 0,
					y1: 0,
					y2: 0,
					y3: 0,
				};
				data.push(d);
			}

			if (idx === 0) {
				d.y0 = graphData.yCount;
			}

			if (idx === 1) {
				d.y1 = graphData.yCount;
			}

			if (idx === 2) {
				d.y2 = graphData.yCount;
			}

			if (idx === 3) {
				d.y3 = graphData.yCount;
			}
		});
	});
	return data;
}

export const PercentageDistributionGraph = ({
	graphs,
}: PercentageDistributionGraphProps) => {
	const data: Data[] = createData(graphs);

	return (
		<LineChart
			width={window.innerWidth * 0.98}
			height={250}
			data={data?.sort(
				(a, b) => a.predictionPercent - b.predictionPercent,
			)}
			margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
		>
			<CartesianGrid strokeDasharray="3 3" />
			<XAxis dataKey="predictionPercent" />
			<YAxis />
			<Tooltip
				labelFormatter={(label, payload) => {
					if (!payload[0] || !payload[1]) {
						return label;
					}

					const v1 = (payload[0].value as number) || 0;
					const v2 = (payload[1].value as number) || 0;

					const percent = (v1 / (v1 + v2)) * 100;
					return `${label}: ${percent.toFixed(2)}`;
				}}
			/>
			<Legend />
			{graphs.map((val, idx) => {
				return (
					<Line
						key={uniqueId()}
						dataKey={`y${idx}`}
						name={val.name}
						stroke={
							val.color ? colord(val.color).toHex() : "#8884d8"
						}
					/>
				);
			})}
		</LineChart>
	);
};

export type PercentageDistributionGraphProps = {
	graphs: PercentageDistributionGraphData[];
};

export type PercentageDistributionGraphData = {
	data?: PercentCountCoordinate[];
	name: string;
	color?: RgbColor;
};

export type PercentCountCoordinate = {
	xPredictionPercent: number;
	yCount: number;
};

type Data = {
	predictionPercent: number;
	y0: number;
	y1: number;
	y2: number;
	y3: number;
};
