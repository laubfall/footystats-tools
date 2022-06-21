import React from 'react';
import {
  CartesianGrid,
  Legend,
  Line,
  XAxis,
  YAxis,
  LineChart,
  Tooltip,
} from 'recharts';
import { uniqueId } from 'lodash';
import { PredictionPercentDistribution } from '../../app/services/prediction/PredictionQualityService.types';

export type PercentageDistributionGraphProps = {
  graphs: PercentageDistributionGraphData[];
};

export type PercentageDistributionGraphData = {
  data?: PredictionPercentDistribution[];
  name: string;
};

type Data = {
  predictionPercent: number;
  y0: number;
  y1: number;
  y2: number;
  y3: number;
};

function createData(graphs: PercentageDistributionGraphData[]): Data[] {
  const data: Data[] = [];
  graphs.forEach((value, idx) => {
    value.data?.forEach((graphData) => {
      let d = data.find(
        (v) => v.predictionPercent === graphData.predictionPercent
      );
      if (d === undefined) {
        d = {
          predictionPercent: graphData.predictionPercent,
          y0: 0,
          y1: 0,
          y2: 0,
          y3: 0,
        };
        data.push(d);
      }

      if (idx === 0) {
        d.y0 = graphData.count;
      }

      if (idx === 1) {
        d.y1 = graphData.count;
      }

      if (idx === 2) {
        d.y2 = graphData.count;
      }

      if (idx === 3) {
        d.y3 = graphData.count;
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
      width={730}
      height={250}
      data={data?.sort((a, b) => a.predictionPercent - b.predictionPercent)}
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
            stroke="#8884d8"
          />
        );
      })}
    </LineChart>
  );
};
