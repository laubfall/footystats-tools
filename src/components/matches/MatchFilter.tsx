/* eslint-disable react-hooks/exhaustive-deps */
import { capitalize } from 'lodash';
import React, { useEffect, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import DateTimePicker from 'react-datetime-picker';
import Select, { PropsValue, SingleValue } from 'react-select';
import IpcAppControllService from '../../app/services/application/IpcAppControllService';
import { NDate, NString } from '../../app/types/General';

export type SelectOption = {
  label: string;
  value: NString;
};

export type FilterSettings = {
  timeFrom: NDate;
  timeUntil: NDate;
  country: NString;
  league: NString;
};

export type MatchFilterHocProps = {
  timeFromChanged?: (date: Date) => void;
  timeUntilChanged?: (date: Date) => void;
  countryChanged?: (newSelectedCountry: SingleValue<SelectOption>) => void;
  leagueChanged?: (newSelectedLeague: SingleValue<SelectOption>) => void;
  somethingChanged?: (actualFilter: FilterSettings) => void;
};

export type MatchFilterProps = {
  selectedCountry?: PropsValue<SelectOption>;
  countries: SelectOption[];
  leagues: SelectOption[];
  timeFrom?: NDate;
  timeUntil?: NDate;
} & MatchFilterHocProps;

export const MatchFilter = ({
  selectedCountry,
  countries,
  leagues,
  timeFrom,
  timeUntil,
  timeFromChanged,
  timeUntilChanged,
  countryChanged,
  leagueChanged,
}: MatchFilterProps) => (
  <Row>
    <Col>
      <Select
        options={countries}
        value={selectedCountry}
        onChange={countryChanged}
      />
    </Col>
    <Col>
      <Select options={leagues} onChange={leagueChanged} />
    </Col>
    <Col>
      <DateTimePicker value={timeFrom as Date} onChange={timeFromChanged} />
    </Col>
    <Col>
      <DateTimePicker value={timeUntil as Date} onChange={timeUntilChanged} />
    </Col>
  </Row>
);

export const MatchFilterHoc = (props: MatchFilterHocProps) => {
  const [countries, setCountries] = useState<SelectOption[]>([]);
  const countryOptionAll: SelectOption = {
    label: 'All',
    value: null,
  };
  const [selectedCountry, setSelectedCountry] =
    useState<SingleValue<SelectOption>>(countryOptionAll);

  const [leagues, setLeagues] = useState<SelectOption[]>([]);
  const leagueOptionAll: SelectOption = {
    label: 'All',
    value: null,
  };

  const [timeFrom, setTimeFrom] = useState<NDate>(null);
  const [timeUntil, setTimeUntil] = useState<NDate>(null);

  useEffect(() => {
    const appControllService = new IpcAppControllService();
    // eslint-disable-next-line promise/catch-or-return
    appControllService.findCountries().then((availableCountries) => {
      const cSelOptions: SelectOption[] = [countryOptionAll];
      availableCountries.forEach((c) => {
        cSelOptions.push({
          label: capitalize(c.name),
          value: capitalize(c.name),
        });
      });
      setCountries(cSelOptions);
      return null;
    });
  }, []);

  useEffect(() => {
    const appControllService = new IpcAppControllService();
    const lSelOptions: SelectOption[] = [leagueOptionAll];
    if (selectedCountry === countryOptionAll) {
      appControllService
        .findCountries()
        .then((availableCountries) => {
          availableCountries.forEach((c) => {
            const availableLeagues =
              c.leagues?.map(
                (league) =>
                  ({
                    label: league.name,
                    value: league.name,
                  } as SelectOption)
              ) || [];
            lSelOptions.push(...availableLeagues);
            setLeagues(lSelOptions);
          });
          return undefined;
        })
        .catch((reason) => undefined);
    }

    return undefined;
  }, [selectedCountry]);

  function onChangeCountry(country: SingleValue<SelectOption>) {
    if (props.countryChanged) {
      props.countryChanged(country);
    }

    if (props.somethingChanged && country != null) {
      props.somethingChanged({
        country: country.value,
        league: null,
        timeFrom,
        timeUntil,
      });
    }

    setSelectedCountry(country);
  }

  function onChangeLeague(league: SingleValue<SelectOption>) {
    if (props.leagueChanged) {
      props.leagueChanged(league);
    }
  }

  function onTimeFromChanged(timeFromNew: Date) {
    if (props.somethingChanged && selectedCountry != null) {
      props.somethingChanged({
        country: selectedCountry.value,
        league: null,
        timeFrom: timeFromNew,
        timeUntil,
      });
    }

    setTimeFrom(timeFromNew);
  }

  function onTimeUntilChanged(timeUntilNew: Date) {
    if (props.somethingChanged && selectedCountry != null) {
      props.somethingChanged({
        country: selectedCountry.value,
        league: null,
        timeFrom,
        timeUntil: timeUntilNew,
      });
    }

    setTimeUntil(timeUntilNew);
  }

  return (
    <>
      <MatchFilter
        leagues={leagues}
        countries={countries}
        selectedCountry={selectedCountry}
        countryChanged={onChangeCountry}
        leagueChanged={onChangeLeague}
        timeFromChanged={onTimeFromChanged}
        timeUntilChanged={onTimeUntilChanged}
        timeFrom={timeFrom}
        timeUntil={timeUntil}
      />
    </>
  );
};
