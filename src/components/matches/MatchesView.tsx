import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import prediction from '../../app/services/prediction/PredictionService';
import IpcMatchStatsService from '../../app/services/stats/IpcMatchStatsService';
import { NDate, NString } from '../../app/types/General';
import { Bet } from '../../app/types/prediction/BetPredictionContext';
import { MatchStats } from '../../app/types/stats/MatchStats';
import { MatchFilterHoc } from './MatchFilter';
import { MatchList, MatchListEntry } from './MatchList';

export const MatchesView = () => {
  const [matches, setMatches] = useState<MatchListEntry[]>([]);

  function calcPredictions(n: MatchStats[]) {
    const r = n.map((ms) => {
      const predictionNumber = prediction({
        bet: Bet.OVER_ZERO_FIVE,
        match: ms,
        leagueStats: undefined,
        teamStats: undefined,
      });

      const mle: MatchListEntry = {
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
    until: NDate
  ) {
    const mss = new IpcMatchStatsService();
    // eslint-disable-next-line promise/always-return
    mss
      .matchesByFilter(country, league, from, until)
      // eslint-disable-next-line promise/always-return
      .then((n) => {
        calcPredictions(n);
      })
      .catch((reason) => console.log(reason));
  }

  useEffect(() => {
    loadMatches(null, null, null, null);
  }, []);

  return (
    <>
      <Row>
        <Col md={9}>
          <MatchFilterHoc
            somethingChanged={(filter) => {
              loadMatches(filter.country, filter.league, null, null);
            }}
          />
        </Col>
        <Col>
          <Button name="doFilter">Filtern</Button>
        </Col>
      </Row>
      <MatchList entries={matches} />
    </>
  );
};

export default MatchesView;
