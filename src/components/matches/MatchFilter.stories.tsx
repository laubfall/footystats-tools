import React, { useState } from 'react';
import { SingleValue } from 'react-select';
import MatchFilter, { MatchFilterProps, SelectOption } from './MatchFilter';

export default {
  title: 'Components/match filter',
};

export const MatchFilterStory = () => {
  const [selectedLeague, setSelectedLeague] = useState<
    SingleValue<SelectOption>
  >();
  const [timeFrom, setTimeFrom] = useState<Date>(new Date());
  const [timeUntil, setTimeUntil] = useState<Date>(new Date());

  const matchFilterProps: MatchFilterProps = {
    timeFrom,
    timeUntil,
    leagues: [
      { label: 'Bundesliga', value: 'Bundesliga' },
      { label: 'Bundesliga 2', value: 'Bundesliga2' },
    ],
    leagueChanged: (val) => setSelectedLeague(val),
    timeFromChanged: (from) => setTimeFrom(from),
    timeUntilChanged: (until) => setTimeUntil(until),
  };
  return (
    <>
      <span>{selectedLeague}</span>
      <span>{timeFrom}</span>
      <span>{timeUntil}</span>
      <MatchFilter
        leagueChanged={matchFilterProps.leagueChanged}
        leagues={matchFilterProps.leagues}
        timeFrom={matchFilterProps.timeFrom}
        timeUntil={matchFilterProps.timeUntil}
        timeFromChanged={matchFilterProps.timeFromChanged}
        timeUntilChanged={matchFilterProps.timeUntilChanged}
      />
    </>
  );
};
