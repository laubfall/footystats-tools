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
import { PredictionPercentDistribution } from '../../app/services/prediction/PredictionQualityService.types';

export type PercentageDistributionGraphProps = {
  data?: PredictionPercentDistribution[];
};

export const PercentageDistributionGraph = ({
  data,
}: PercentageDistributionGraphProps) => {
  return (
    <LineChart
      width={730}
      height={250}
      data={data?.sort((a, b) => a.predictionPercent - b.predictionPercent)}
      margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="predictionPercent" domain={[0, 100]} />
      <YAxis domain={[0, 700]} />
      <Tooltip />
      <Legend />
      <Line dataKey="count" stroke="#8884d8" />
    </LineChart>
  );
};
