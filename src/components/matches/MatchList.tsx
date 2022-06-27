import React from 'react';
import DataTable, { SortOrder, TableColumn } from 'react-data-table-component';
import {
  BsFillArrowDownCircleFill,
  BsFillArrowUpCircleFill,
} from 'react-icons/bs';
import {
  PaginationChangePage,
  PaginationChangeRowsPerPage,
} from 'react-data-table-component/dist/src/DataTable/types';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import translate from '../../i18n/translate';
import { PredictionResult } from '../../app/services/prediction/PredictionService.types';

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
  footyStatsUrl: string;
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

function createBetPredictionColumns(predictionForBets?: Bet[]) {
  return (
    predictionForBets?.map((bet) => {
      const tr: TableColumn<MatchListEntry> = {
        name: translate(`renderer.matchesview.bet.${Bet[bet]}`),
        selector: (row) => {
          const betPrediction = row.betPredictions.find((v) => v.bet === bet);
          return `${betPrediction?.prediction.betSuccessInPercent}`;
        },
        // eslint-disable-next-line react/display-name
        cell: (row) => {
          const b = row.betPredictions.find((v) => v.bet === bet);
          return (
            <>
              {b?.prediction.betSuccessInPercent}
              {b?.prediction.betOnThis === true && (
                <>
                  &nbsp;
                  <BsFillArrowUpCircleFill />
                </>
              )}
              {b?.prediction.betOnThis === false && (
                <>
                  &nbsp;
                  <BsFillArrowDownCircleFill />
                </>
              )}
            </>
          );
        },
        conditionalCellStyles: [
          {
            when: (row) => {
              // completed and betOnThis and correct
              const betPrediction = row.betPredictions.find(
                (v) => v.bet === bet
              )?.prediction;
              return (
                betPrediction !== undefined &&
                betPrediction.analyzeResult === 'SUCCESS'
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
              const betPrediction = row.betPredictions.find(
                (v) => v.bet === bet
              )?.prediction;
              return (
                betPrediction !== undefined &&
                betPrediction.analyzeResult === 'FAILED'
              );
            },
            style: {
              color: 'white',
              backgroundColor: 'red',
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
      name: translate('renderer.matchlist.table.col.one'),
      selector: (row) => row.gameStartsAt,
      sortable: true,
      sortField: 'date_unix',
    },
    {
      name: translate('renderer.matchlist.table.col.two'),
      selector: (row) => row.homeTeam,
    },
    {
      name: translate('renderer.matchlist.table.col.three'),
      selector: (row) => row.awayTeam,
    },
    {
      name: translate('renderer.matchlist.table.col.four'),
      selector: (row) => row.country,
    },
    {
      name: translate('renderer.matchlist.table.col.five'),
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
          window.open(`https://footystats.org${row.footyStatsUrl}`, '_blank');
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
