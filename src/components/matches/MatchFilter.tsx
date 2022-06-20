/* eslint-disable react-hooks/exhaustive-deps */
import { capitalize } from 'lodash';
import log from 'electron-log';
import React, { useEffect, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import DateTimePicker from 'react-datetime-picker';
import Select, { MultiValue, PropsValue } from 'react-select';
import IpcAppControllService from '../../app/services/application/IpcAppControllService';
import { NDate, NString } from '../../app/types/General';

export type SelectOption = {
  label: string;
  value: NString;
};

export type FilterSettings = {
  timeFrom: NDate;
  timeUntil: NDate;
  country: NString[];
  league: NString[];
};

export type MatchFilterHocProps = {
  timeFromChanged?: (date: Date) => void;
  timeUntilChanged?: (date: Date) => void;
  countryChanged?: (newSelectedCountry: MultiValue<SelectOption>) => void;
  leagueChanged?: (newSelectedLeague: MultiValue<SelectOption>) => void;
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
        isMulti
      />
    </Col>
    <Col>
      <Select options={leagues} onChange={leagueChanged} isMulti />
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

  const [selectedCountry, setSelectedCountry] =
    useState<MultiValue<SelectOption>>();

  const [leagues, setLeagues] = useState<SelectOption[]>([]);

  const [selectedLeagues, setSelectedLeagues] =
    useState<MultiValue<SelectOption>>();

  const [timeFrom, setTimeFrom] = useState<NDate>(null);
  const [timeUntil, setTimeUntil] = useState<NDate>(null);

  useEffect(() => {
    const appControllService = new IpcAppControllService();
    // eslint-disable-next-line promise/catch-or-return
    appControllService.findCountries().then((availableCountries) => {
      const cSelOptions: SelectOption[] = [];
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
    const lSelOptions: SelectOption[] = [];
    if (selectedCountry?.length === 0) {
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
        .catch((reason) =>
          log.error(
            'MatchFilter: Failed to retrieve available countries',
            reason
          )
        );
    } else {
      const availableLeagues: SelectOption[] = [];
      appControllService
        .findCountries()
        .then((availableCountries) => {
          selectedCountry?.forEach((sc) => {
            const fc = availableCountries.find((c) => c.name === sc.value);
            const l =
              fc?.leagues?.map(
                (league) =>
                  ({
                    label: league.name,
                    value: league.name,
                  } as SelectOption)
              ) || [];
            availableLeagues.push(...l);
          });
          return null;
        })
        .catch((reason) =>
          log.error(
            'MatchFilter: Failed to retrieve available countries',
            reason
          )
        );
      setLeagues(availableLeagues);
    }

    return undefined;
  }, [selectedCountry]);

  function onChangeCountry(country: MultiValue<SelectOption>) {
    if (props.countryChanged) {
      props.countryChanged(country);
    }

    if (props.somethingChanged && country != null) {
      const countryNames = country.map((mvc) => mvc.value);
      props.somethingChanged({
        country: countryNames,
        league: selectedLeagues?.map((sl) => sl.value) || [],
        timeFrom,
        timeUntil,
      });
    }

    setSelectedCountry(country);
  }

  function onChangeLeague(league: MultiValue<SelectOption>) {
    if (props.leagueChanged) {
      props.leagueChanged(league);
    }

    if (props.somethingChanged && league != null) {
      const leagueNames = league.map((mvc) => mvc.value);
      props.somethingChanged({
        country: selectedCountry?.map((sc) => sc.value) || [],
        league: leagueNames,
        timeFrom,
        timeUntil,
      });
    }

    setSelectedLeagues(league);
  }

  function onTimeFromChanged(timeFromNew: Date) {
    if (props.somethingChanged && selectedCountry != null) {
      props.somethingChanged({
        country: selectedCountry.map((mvc) => mvc.value),
        league: [],
        timeFrom: timeFromNew,
        timeUntil,
      });
    }

    setTimeFrom(timeFromNew);
  }

  function onTimeUntilChanged(timeUntilNew: Date) {
    if (props.somethingChanged && selectedCountry != null) {
      props.somethingChanged({
        country: selectedCountry.map((mvc) => mvc.value),
        league: [],
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
