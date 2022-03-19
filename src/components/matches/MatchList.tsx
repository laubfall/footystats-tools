import { uniqueId } from 'lodash';
import React from 'react';
import { Col, Row } from 'react-bootstrap';
import DataTable, { TableColumn } from 'react-data-table-component';

export type BetPrediction = {
  betName: string;
  prediction: number;
};

export type MatchListEntry = {
  gameStartsAt: string;
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
  const columns: TableColumn<MatchListEntry>[] = [
    {
      name: 'Spielbeginn',
      selector: (row) => row.gameStartsAt,
    },
    {
      name: 'Heimteam',
      selector: (row) => row.homeTeam,
    },
    {
      name: 'Auswärtsteam',
      selector: (row) => row.awayTeam,
    },
    {
      name: 'Land',
      selector: (row) => row.country,
    },
  ];

  return (
    <>
      <DataTable columns={columns} data={entries} pagination />
    </>
  );
};
