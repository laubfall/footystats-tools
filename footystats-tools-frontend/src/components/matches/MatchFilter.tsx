import React, { useEffect, useState } from "react";
import { Col, Row } from "react-bootstrap";
import DateTimePicker from "react-datetime-picker";
import Select, { MultiValue, PropsValue } from "react-select";
import { NString } from "../../app/types/General";
import countriesAndLeagues from "../../app/countriesAndLeagues";

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
			<DateTimePicker
				value={timeFrom as Date}
				onChange={timeFromChanged}
			/>
		</Col>
		<Col>
			<DateTimePicker
				value={timeUntil as Date}
				onChange={timeUntilChanged}
			/>
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

	const [timeFrom, setTimeFrom] = useState<Date>(undefined);
	const [timeUntil, setTimeUntil] = useState<Date>(undefined);

	useEffect(() => {
		const cSelOptions: SelectOption[] = [];
		countriesAndLeagues.Countries.forEach((c) => {
			cSelOptions.push({
				label: c.name,
				value: c.name,
			});
		});
		setCountries(cSelOptions);
	}, []);

	useEffect(() => {
		const lSelOptions: SelectOption[] = [];

		countriesAndLeagues.Countries.filter((c) => {
			if (selectedCountry?.length === 0) {
				return true;
			}

			return (
				selectedCountry?.find((sl) => sl.value === c.name) !== undefined
			);
		}).forEach((c) => {
			const availableLeagues =
				c.leagues?.map(
					(league) =>
						({
							label: league.name,
							value: league.name,
						} as SelectOption),
				) || [];
			lSelOptions.push(...availableLeagues);
			setLeagues(lSelOptions);
		});
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
		if (props.somethingChanged) {
			props.somethingChanged({
				country: selectedCountry?.map((mvc) => mvc.value),
				league: [],
				timeFrom: timeFromNew === null ? undefined : timeFromNew,
				timeUntil,
			});
		}

		setTimeFrom(timeFromNew);
	}

	function onTimeUntilChanged(timeUntilNew: Date) {
		if (props.somethingChanged) {
			props.somethingChanged({
				country: selectedCountry?.map((mvc) => mvc.value),
				league: [],
				timeFrom,
				timeUntil: timeUntilNew === null ? undefined : timeUntilNew,
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

export type SelectOption = {
	label: string;
	value: NString;
};

export type FilterSettings = {
	timeFrom: Date;
	timeUntil: Date;
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
	timeFrom?: Date;
	timeUntil?: Date;
} & MatchFilterHocProps;
