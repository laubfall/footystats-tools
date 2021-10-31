import React from 'react';

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
      <>{e.awayTeam}</>
    ))}
  </>
);
