import React from "react";
import { Col, Row } from "react-bootstrap";
import { InfluencerDistributionScatterChartView } from "./InfluencerDistributionScatterChart";
import TestData from "../../../testdata/storybook/predictionQuality/InfluencerDistributionGraph.json";
import { BetPredictionQuality } from "../../app/services/prediction/PredictionQualityService.types";

export default {
  title: "Components/predictionQuality/InfluencerDistributionGrpah",
};

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
const td: BetPredictionQuality = TestData.measurements[1];

export const Story = () => (
  <>
    <Row>
      <Col>
        {/* eslint-disable-next-line react/no-unescaped-entities */}
        <h2>Successful bet and don't bet predictions</h2>
        <InfluencerDistributionScatterChartView
          distributionBetSuccess={td.distributionBetOnThis || []}
          distributionBetFailed={td.distributionDontBetOnThis || []}
        />
      </Col>
      <Col>
        {/* eslint-disable-next-line react/no-unescaped-entities */}
        <h2>Failed bet and don't bet predictions</h2>
        <InfluencerDistributionScatterChartView
          distributionBetSuccess={td.distributionBetOnThisFailed || []}
          distributionBetFailed={td.distributionDontBetOnThisFailed || []}
        />
      </Col>
    </Row>
  </>
);
