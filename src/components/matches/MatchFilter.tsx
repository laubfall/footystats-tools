/* eslint-disable react-hooks/exhaustive-deps */
import React, { useEffect, useState } from 'react';
import { Col, Row } from 'react-bootstrap';
import DateTimePicker from 'react-datetime-picker';
import Select, { PropsValue, SingleValue } from 'react-select';
import IpcAppControllService from '../../app/services/application/IpcAppControllService';

export type SelectOption = {
  label: string;
  value: any;
};

export type FilterSettings = {
  timeFrom?: Date;
  timeUntil?: Date;
  country: SelectOption;
  league?: SelectOption;
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
  timeFrom?: Date;
  timeUntil?: Date;
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
      <DateTimePicker value={timeFrom} onChange={timeFromChanged} />
    </Col>
    <Col>
      <DateTimePicker value={timeUntil} onChange={timeUntilChanged} />
    </Col>
  </Row>
);

export const MatchFilterHoc = (props: MatchFilterHocProps) => {
  const [countries, setCountries] = useState<SelectOption[]>([]);
  const countryOptionAll: SelectOption = {
    label: 'All',
    value: 'all',
  };
  const [selectedCountry, setSelectedCountry] =
    useState<SelectOption>(countryOptionAll);

  const [leagues, setLeagues] = useState<SelectOption[]>([]);
  const leagueOptionAll: SelectOption = {
    label: 'All',
    value: 'all',
  };

  const [timeFrom, setTimeFrom] = useState<Date>();
  const [timeUntil, setTimeUntil] = useState<Date>();

  useEffect(() => {
    const appControllService = new IpcAppControllService();
    // eslint-disable-next-line promise/catch-or-return
    appControllService.findCountries().then((availableCountries) => {
      const cSelOptions: SelectOption[] = [countryOptionAll];
      availableCountries.forEach((c) => {
        cSelOptions.push({
          label: c.name,
          value: c.name,
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

    setSelectedCountry(country?.value);
  }

  function onChangeLeague(league: SingleValue<SelectOption>) {
    if (props.leagueChanged) {
      props.leagueChanged(league);
    }
  }

  function onTimeFromChanged(timeFromNew: Date) {
    if (props.somethingChanged) {
      props.somethingChanged({
        country: selectedCountry?.value,
        league: undefined,
        timeFrom: timeFromNew,
        timeUntil,
      });
    }

    setTimeFrom(timeFromNew);
  }

  function onTimeUntilChanged(timeUntilNew: Date) {
    if (props.somethingChanged) {
      props.somethingChanged({
        country: selectedCountry?.value,
        league: undefined,
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
