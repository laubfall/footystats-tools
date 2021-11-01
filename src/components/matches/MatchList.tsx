import React from 'react';
import { Button, Col, Row } from 'react-bootstrap';

export interface BetPrediction {
  betName: string;
  prediction: number;
}

export interface MatchListEntry {
  awayTeam: string;
  homeTeam: string;
  betPredictions: BetPrediction[];
}

export interface MatchListProps {
  entries: MatchListEntry[];
}

// eslint-disable-next-line import/prefer-default-export
export const MatchList = ({ entries }: MatchListProps) => (
  <>
    {entries.map((e) => (
      <>
        <Row>
          <Col>{e.awayTeam}</Col>
          <Col>
            <Button name="test" value="test" variant="secondary">
              fkdsljf
            </Button>
          </Col>
        </Row>
      </>
    ))}
  </>
);
