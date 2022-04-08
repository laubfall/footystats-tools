import React from 'react';
import DataTable, { SortOrder, TableColumn } from 'react-data-table-component';
import {
  PaginationChangePage,
  PaginationChangeRowsPerPage,
} from 'react-data-table-component/dist/src/DataTable/types';
import { Bet } from '../../app/types/prediction/BetPredictionContext';

export type BetPrediction = {
  bet: Bet;
  prediction: number;
};

export type MatchListEntry = {
  gameStartsAt: string;
  awayTeam: string;
  homeTeam: string;
  country: string;
  result: string;
  betPredictions: BetPrediction[];
};

export type MatchListProps = {
  entries: MatchListEntry[];
  totalRows: number;
  sortHandler: SortHandler;
  pageChange?: PaginationChangePage;
  pageSizeChange?: PaginationChangeRowsPerPage;
  predictionForBets?: Bet[];
};

// eslint-disable-next-line import/prefer-default-export
export const MatchList = ({
  entries,
  totalRows,
  sortHandler,
  pageChange,
  pageSizeChange,
  predictionForBets,
}: MatchListProps) => {
  const predictionColumns =
    predictionForBets?.map((bet) => {
      const tr: TableColumn<MatchListEntry> = {
        name: bet,
        selector: (row) =>
          row.betPredictions.find((v) => v.bet === bet)?.prediction || '',
      };
      return tr;
    }) || [];

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
    {
      name: 'Ergebnis',
      selector: (row) => row.result,
    },
    ...predictionColumns,
  ];

  return (
    <>
      <DataTable
        columns={columns}
        data={entries}
        onSort={sortHandler}
        onChangePage={pageChange}
        onChangeRowsPerPage={pageSizeChange}
        paginationTotalRows={totalRows}
        pagination
        sortServer
        paginationServer
      />
    </>
  );
};

export type SortHandler = {
  (column: TableColumn<MatchListEntry>, sortDirection: SortOrder): void;
};
