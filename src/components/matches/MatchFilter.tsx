import React from 'react';
import { Col, Row } from 'react-bootstrap';
import DateTimePicker from 'react-datetime-picker';
import Select, { SingleValue } from 'react-select';

export type SelectOption = {
  label: string;
  value: any;
};

export type MatchFilterProps = {
  leagues: SelectOption[];
  timeFrom: Date;
  timeUntil: Date;
  timeFromChanged: (date: Date) => void;
  timeUntilChanged: (date: Date) => void;
  leagueChanged: (newSelectedLeague: SingleValue<SelectOption>) => void;
};

const MatchFilter = ({
  leagues,
  timeFrom,
  timeUntil,
  timeFromChanged,
  timeUntilChanged,
  leagueChanged,
}: MatchFilterProps) => {
  return (
    <Row>
      <Col>
        <Select options={leagues} onChange={leagueChanged} />
      </Col>
      <Col>
        von: <DateTimePicker value={timeFrom} onChange={timeFromChanged} />
      </Col>
      <Col>
        bis: <DateTimePicker value={timeUntil} onChange={timeUntilChanged} />
      </Col>
    </Row>
  );
};

export default MatchFilter;
