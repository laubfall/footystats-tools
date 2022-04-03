import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import {
  CursorModification,
  PagedResult,
  SortOrder,
} from '../../app/services/application/DbStoreService';
import prediction from '../../app/services/prediction/PredictionService';
import IpcMatchStatsService from '../../app/services/stats/IpcMatchStatsService';
import { NDate, NString } from '../../app/types/General';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import { MatchStats } from '../../app/types/stats/MatchStats';
import { FilterSettings, MatchFilterHoc } from './MatchFilter';
import { MatchList, MatchListEntry, SortHandler } from './MatchList';

export const MatchesView = () => {
  const [matches, setMatches] = useState<MatchListEntry[]>([]);
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

      const mle: MatchListEntry = {
        gameStartsAt: ms.date_GMT,
        awayTeam: ms['Away Team'],
        homeTeam: ms['Home Team'],
        country: ms.Country,
        betPredictions: [{ betName: 'Over', prediction: predictionNumber }],
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
        calcPredictions(n[1]);
      })
      .catch((reason) => console.log(reason));
  }

  const sortHandler: SortHandler = (column, sortOrder) => {
    let sort: SortOrder = -1;
    if (sortOrder === 'asc') {
      sort = 1;
    } else {
      sort = -1;
    }
    loadMatches(
      filter.country,
      filter.league,
      filter.timeFrom,
      filter.timeUntil,
      [
        {
          modification: 'sort',
          parameter: { [column.sortField as string]: sort },
        },
      ]
    );
  };

  useEffect(() => {
    loadMatches(null, null, null, null, [
      { modification: 'limit', parameter: 5 },
    ]);
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
                changedFilter.timeUntil
              );
              setFilter(changedFilter);
            }}
          />
        </Col>
        <Col>
          <Button name="doFilter">Filtern</Button>
        </Col>
      </Row>
      <MatchList entries={matches} sortHandler={sortHandler} />
    </>
  );
};

export default MatchesView;
