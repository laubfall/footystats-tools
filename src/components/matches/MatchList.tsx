import React from 'react';
import DataTable, { SortOrder, TableColumn } from 'react-data-table-component';

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
  sortHandler: SortHandler;
};

// eslint-disable-next-line import/prefer-default-export
export const MatchList = ({ entries, sortHandler }: MatchListProps) => {
  const columns: TableColumn<MatchListEntry>[] = [
    {
      name: 'Spielbeginn',
      selector: (row) => row.gameStartsAt,
      sortable: true,
      sortField: 'date_unix',
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
      <DataTable
        columns={columns}
        data={entries}
        onSort={sortHandler}
        pagination
        sortServer
      />
    </>
  );
};

export type SortHandler = {
  (column: TableColumn<MatchListEntry>, sortDirection: SortOrder): void;
};
