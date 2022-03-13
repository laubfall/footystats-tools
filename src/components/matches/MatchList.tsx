import { uniqueId } from 'lodash';
import React from 'react';
import { Col, Row } from 'react-bootstrap';

export type BetPrediction = {
  betName: string;
  prediction: number;
};

export type MatchListEntry = {
  awayTeam: string;
  homeTeam: string;
  country: string;
  betPredictions: BetPrediction[];
};

export type MatchListProps = {
  entries: MatchListEntry[];
};

// eslint-disable-next-line import/prefer-default-export
export const MatchList = ({ entries }: MatchListProps) => {
  return (
    <>
      {entries.map((e) => (
        <>
          <Row key={uniqueId()}>
            <Col>{e.awayTeam}</Col>
            <Col>{e.homeTeam}</Col>
            <Col>{e.country}</Col>
            {e.betPredictions?.map((bp) => {
              return <Col key={uniqueId()}>{bp.prediction}</Col>;
            })}
          </Row>
        </>
      ))}
    </>
  );
};
