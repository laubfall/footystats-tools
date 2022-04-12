import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import {
  PaginationChangePage,
  PaginationChangeRowsPerPage,
} from 'react-data-table-component/dist/src/DataTable/types';
import {
  CursorModification,
  SortOrder,
} from '../../app/services/application/DbStoreService';
import { subscribeMsgSimpleMessage } from '../../app/services/application/gui/IpcMain2Renderer';
import prediction from '../../app/services/prediction/PredictionService';
import IpcMatchStatsService from '../../app/services/stats/IpcMatchStatsService';
import { NDate, NString } from '../../app/types/General';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import { MatchStats } from '../../app/types/stats/MatchStats';
import { FilterSettings, MatchFilterHoc } from './MatchFilter';
import { MatchList, MatchListEntry, SortHandler } from './MatchList';

function createMatchListConstraints(
  page: number,
  sizePerPage: number,
  sortOrder: SortOrder,
  sortColumn?: string
): CursorModification[] {
  return [
    { modification: 'limit', parameter: sizePerPage },
    { modification: 'skip', parameter: page * sizePerPage },
    {
      modification: 'sort',
      parameter: { [sortColumn as string]: sortOrder },
    },
  ];
}

export const MatchesView = () => {
  const [matches, setMatches] = useState<MatchListEntry[]>([]);
  const [totalRows, setTotalRows] = useState<number>(0);
  const [perPage, setPerPage] = useState(10);
  const [page, setPage] = useState(0);
  const [sortColumn, setSortColumn] = useState<string | undefined>('date_unix');
  const [sortOrder, setSortOrder] = useState<SortOrder>(-1);
  const [filter, setFilter] = useState<FilterSettings>({
    country: null,
    league: null,
    timeFrom: null,
    timeUntil: null,
  });

  function calcPredictions(n: MatchStats[]) {
    const r = n.map((ms) => {
      const predictionNumber = prediction({
        bet: Bet.OVER_ZERO_FIVE,
        match: ms,
        leagueStats: undefined,
        teamStats: undefined,
      });
      const bttsYesPredictionNumber = prediction({
        bet: Bet.BTTS_YES,
        match: ms,
        leagueStats: undefined,
        teamStats: undefined,
      });

      const mle: MatchListEntry = {
        gameStartsAt: ms.date_GMT,
        awayTeam: ms['Away Team'],
        homeTeam: ms['Home Team'],
        country: ms.Country,
        result:
          ms['Match Status'] === 'complete'
            ? `
        ${ms['Result - Home Team Goals']} : ${ms['Result - Away Team Goals']}`
            : '-',
        betPredictions: [
          { bet: Bet.OVER_ZERO_FIVE, prediction: predictionNumber },
          { bet: Bet.BTTS_YES, prediction: bttsYesPredictionNumber },
        ],
      };

      return mle;
    });
    setMatches(r);
  }

  function loadMatches(
    country: NString,
    league: NString,
    from: NDate,
    until: NDate,
    cursorModification?: CursorModification[]
  ) {
    const mss = new IpcMatchStatsService();
    // eslint-disable-next-line promise/always-return
    mss
      .matchesByFilter(country, league, from, until, cursorModification)
      // eslint-disable-next-line promise/always-return
      .then((n) => {
        setTotalRows(n[0]);
        calcPredictions(n[1]);
      })
      .catch((reason) => console.log(reason));
  }

  const sortHandler: SortHandler = (column, newSortOrder) => {
    const sortThatWay = newSortOrder === 'asc' ? 1 : -1;
    setSortOrder(sortThatWay);
    setSortColumn(column.sortField);
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      createMatchListConstraints(page, perPage, sortThatWay, column.sortField)
    );
  };

  const changePageHandler: PaginationChangePage = (newPage) => {
    setPage(newPage);
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      createMatchListConstraints(newPage, perPage, sortOrder, sortColumn)
    );
  };

  const changePageSizeHandler: PaginationChangeRowsPerPage = (
    newPerPage,
    currentPage
  ) => {
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      createMatchListConstraints(currentPage, newPerPage, sortOrder, sortColumn)
    );
    setPerPage(newPerPage);
  };

  useEffect(() => {
    loadMatches(
      null,
      null,
      null,
      null,
      createMatchListConstraints(page, perPage, sortOrder, sortColumn)
    );

    subscribeMsgSimpleMessage((msg) => {
      loadMatches(
        filter.country,
        filter.league,
        filter.timeFrom,
        filter.timeUntil,
        createMatchListConstraints(page, perPage, sortOrder, sortColumn)
      );
    });
  }, []);

  return (
    <>
      <Row>
        <Col md={9}>
          <MatchFilterHoc
            somethingChanged={(changedFilter) => {
              loadMatches(
                changedFilter.country,
                changedFilter.league,
                changedFilter.timeFrom,
                changedFilter.timeUntil,
                createMatchListConstraints(page, perPage, sortOrder, sortColumn)
              );
              setFilter(changedFilter);
            }}
          />
        </Col>
        <Col>
          <Button name="doFilter">Filtern</Button>
        </Col>
      </Row>
      <MatchList
        entries={matches}
        totalRows={totalRows}
        sortHandler={sortHandler}
        pageChange={changePageHandler}
        pageSizeChange={changePageSizeHandler}
        predictionForBets={[Bet.OVER_ZERO_FIVE, Bet.BTTS_YES]}
      />
    </>
  );
};

export default MatchesView;
