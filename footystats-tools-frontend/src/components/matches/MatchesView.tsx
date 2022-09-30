import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import {
  PaginationChangePage,
  PaginationChangeRowsPerPage,
} from "react-data-table-component/dist/src/DataTable/types";
import { NDate, NString } from "../../app/types/General";
import { Bet } from "../../app/types/prediction/BetPredictionContext";
import { FilterSettings, MatchFilterHoc } from "./MatchFilter";
import {
  BetPrediction,
  MatchList,
  MatchListEntry,
  SortHandler,
} from "./MatchList";
import IpcMatchService from "../../app/services/match/IpcMatchService";
import {
  Match,
  Paging,
  PagingDirectionEnum,
} from "../../footystats-frontendapi";

function matchToBetPrediction(match: Match): BetPrediction[] {
  const betPredictions: BetPrediction[] = [];
  if (match.o05) {
    betPredictions.push({ bet: Bet.OVER_ZERO_FIVE, prediction: match.o05 });
  }

  if (match.bttsYes) {
    betPredictions.push({ bet: Bet.BTTS_YES, prediction: match.bttsYes });
  }

  return betPredictions;
}

function matchListEntries(n: Match[]) {
  const r = n.map(async (ms) => {
    const mle: MatchListEntry = {
      gameStartsAt: ms.dateGMT,
      awayTeam: ms["Away Team"],
      homeTeam: ms["Home Team"],
      country: ms.country,
      result:
        ms.state === "complete"
          ? `
        ${ms.goalsHomeTeam} : ${ms.goalsAwayTeam}`
          : "-",
      footyStatsUrl: ms.footyStatsUrl,
      betPredictions: [
        { bet: Bet.OVER_ZERO_FIVE, prediction: ms.o05 },
        { bet: Bet.BTTS_YES, prediction: ms.bttsYes },
      ],
    };

    return mle;
  });

  return Promise.all(r);
}

export const MatchesView = () => {
  const [matches, setMatches] = useState<MatchListEntry[]>([]);
  const [totalRows, setTotalRows] = useState<number>(0);
  const [perPage, setPerPage] = useState(10);
  const [page, setPage] = useState(0);
  const [sortColumn, setSortColumn] = useState<string | undefined>("date_unix");
  const [sortOrder, setSortOrder] = useState<PagingDirectionEnum>(
    PagingDirectionEnum.Desc,
  );
  const [filter, setFilter] = useState<FilterSettings>({
    country: [],
    league: [],
    timeFrom: null,
    timeUntil: null,
  });

  function createMatchListEntries(n: Match[]) {
    return matchListEntries(n);
  }

  function loadMatches(
    country: NString[],
    league: NString[],
    from: NDate,
    until: NDate,
    paging?: Paging,
  ) {
    const mss = new IpcMatchService();
    mss
      .matchesByFilter(undefined, undefined, from, until, paging)
      .then(async (n) => {
        setTotalRows(n.totalElements);
        const r = await createMatchListEntries(n.elements);
        setMatches(r);
      })
      .catch((reason) =>
        console.error("Failed to load matches for MatchesView", reason),
      );
  }

  const sortHandler: SortHandler = (column, newSortOrder) => {
    const sortThatWay =
      newSortOrder === "asc"
        ? PagingDirectionEnum.Asc
        : PagingDirectionEnum.Desc;
    setSortOrder(sortThatWay);
    setSortColumn(column.sortField);
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      { page: page, size: perPage, direction: sortThatWay },
    );
  };

  const changePageHandler: PaginationChangePage = (newPage) => {
    const newPageValue = newPage - 1;
    setPage(newPageValue);
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      {
        page: newPage,
        size: perPage,
        direction: sortOrder,
      },
    );
  };

  const changePageSizeHandler: PaginationChangeRowsPerPage = (
    newPerPage,
    currentPage,
  ) => {
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      {
        page: currentPage,
        size: newPerPage,
        direction: sortOrder,
      },
    );
    setPerPage(newPerPage);
  };

  useEffect(() => {
    loadMatches([], [], null, null, { page: 0, size: perPage });
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
              );
              setFilter(changedFilter);
            }}
          />
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
