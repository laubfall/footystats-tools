import React from 'react';
import DataTable, { SortOrder, TableColumn } from 'react-data-table-component';
import {
  PaginationChangePage,
  PaginationChangeRowsPerPage,
} from 'react-data-table-component/dist/src/DataTable/types';
import { PredictionResult } from '../../app/services/prediction/PredictionService';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import { MatchStats } from '../../app/types/stats/MatchStats';
import translate from '../../i18n/translate';

export type BetPrediction = {
  bet: Bet;
  prediction: PredictionResult;
};

export type MatchListEntry = {
  gameStartsAt: string;
  awayTeam: string;
  homeTeam: string;
  country: string;
  result: string;
  betPredictions: BetPrediction[];
  matchStats: MatchStats;
};

export type MatchListProps = {
  entries: MatchListEntry[];
  totalRows: number;
  sortHandler: SortHandler;
  pageChange?: PaginationChangePage;
  pageSizeChange?: PaginationChangeRowsPerPage;
  predictionForBets?: Bet[];
};

function createBetPredictionColumns(predictionForBets?: Bet[]) {
  return (
    predictionForBets?.map((bet) => {
      const tr: TableColumn<MatchListEntry> = {
        name: translate(`renderer.matchesview.bet.${Bet[bet]}`),
        selector: (row) => {
          const b = row.betPredictions.find((v) => v.bet === bet);
          return `${b?.prediction.betSuccessInPercent}`;
        },
        conditionalCellStyles: [
          {
            when: (row) => {
              // completed and betOnThis and correct
              const prediction = row.betPredictions.find(
                (v) => v.bet === bet
              )?.prediction;
              return (
                prediction !== undefined &&
                prediction.betOnThis === true &&
                prediction.analyzeResult === 'SUCCESS'
              );
            },
            style: {
              color: 'white',
              backgroundColor: 'green',
            },
          },
          {
            when: (row) => {
              // completed and betOnThis and not correct
              const prediction = row.betPredictions.find(
                (v) => v.bet === bet
              )?.prediction;
              return (
                prediction !== undefined &&
                prediction.betOnThis === true &&
                prediction.analyzeResult === 'FAILED'
              );
            },
            style: {
              color: 'white',
              backgroundColor: 'red',
            },
          },
          {
            when: (row) => {
              // incomplete and betOnThis
              const prediction = row.betPredictions.find(
                (v) => v.bet === bet
              )?.prediction;
              return (
                prediction !== undefined &&
                prediction.betOnThis === true &&
                prediction.analyzeResult === 'NOT_COMPLETED'
              );
            },
            style: {
              background:
                'repeating-linear-gradient(45deg,#ccff99,#ccff99 10px,green 10px,green 20px)',
            },
          },
          {
            when: (row) => {
              // incomplete and not betOnThis
              const prediction = row.betPredictions.find(
                (v) => v.bet === bet
              )?.prediction;
              return (
                prediction !== undefined &&
                prediction.betOnThis === false &&
                prediction.analyzeResult === 'NOT_COMPLETED'
              );
            },
            style: {
              background:
                'repeating-linear-gradient(45deg,#ff9999,#ff9999 10px,red 10px,red 20px)',
            },
          },
        ],
      };
      return tr;
    }) || []
  );
}

// eslint-disable-next-line import/prefer-default-export
export const MatchList = ({
  entries,
  totalRows,
  sortHandler,
  pageChange,
  pageSizeChange,
  predictionForBets,
}: MatchListProps) => {
  const predictionColumns = createBetPredictionColumns(predictionForBets);

  let columns: TableColumn<MatchListEntry>[] = [
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
  ];

  columns = columns.concat(predictionColumns);

  return (
    <>
      <DataTable
        columns={columns}
        data={entries}
        onSort={sortHandler}
        onChangePage={pageChange}
        onChangeRowsPerPage={pageSizeChange}
        onRowDoubleClicked={(row) => {
          window.open(
            `https://footystats.org${row.matchStats['Match FootyStats URL']}`,
            '_blank'
          );
        }}
        paginationTotalRows={totalRows}
        defaultSortFieldId={1}
        defaultSortAsc={false}
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
