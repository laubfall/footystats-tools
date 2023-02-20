import React, { useEffect, useState } from "react";
import { Col, Form, Row } from "react-bootstrap";
import DateTimePicker from "react-datetime-picker";
import Select, { MultiValue, PropsValue } from "react-select";
import { NString } from "../../app/types/General";
import countriesAndLeagues from "../../app/countriesAndLeagues";
import translate from "../../i18n/translate";
import { subMinutes } from "date-fns";

export const MatchFilter = ({
	selectedCountry,
	countries,
	leagues,
	timeFrom,
	timeUntil,
	currentMatches,
	timeFromChanged,
	timeUntilChanged,
	countryChanged,
	leagueChanged,
	currentMatchesChanged,
}: MatchFilterProps) => (
	<>
		<Row className={"m-2"}>
			<Form.Group as={Col} className={"col-3"}>
				<Form.Label>
					{translate("renderer.matchfilter.label.country")}
				</Form.Label>
				<Select
					options={countries}
					value={selectedCountry}
					onChange={countryChanged}
					isMulti
				/>
			</Form.Group>
			<Form.Group as={Col}>
				<Form.Label>
					{translate("renderer.matchfilter.label.league")}
				</Form.Label>
				<Select options={leagues} onChange={leagueChanged} isMulti />
			</Form.Group>
			<Form.Group as={Col} className={"col-2"}>
				<Form.Label>
					{translate("renderer.matchfilter.label.start")}
				</Form.Label>
				<br />
				<DateTimePicker
					value={timeFrom as Date}
					onChange={timeFromChanged}
				/>
			</Form.Group>
			<Form.Group as={Col}>
				<Form.Label>
					{translate("renderer.matchfilter.label.end")}
				</Form.Label>
				<br />
				<DateTimePicker
					value={timeUntil as Date}
					onChange={timeUntilChanged}
				/>
			</Form.Group>
		</Row>
		<Row className={"m-2"}>
			<Form.Group as={Col}>
				<Form.Check
					type="checkbox"
					label={translate(
						"renderer.matchfilter.label.currentmatches",
					)}
					checked={currentMatches}
					onChange={(e) => currentMatchesChanged(e.target.checked)}
				/>
			</Form.Group>
		</Row>
	</>
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
	const [currentMatches, setCurrentMatches] = useState(false);

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
			const [timeFrom, timeUntil] = startAndEnd(currentMatches);
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
			const [timeFrom, timeUntil] = startAndEnd(currentMatches);
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

	function onCurrentMatches(checked: boolean) {
		setCurrentMatches(checked);
		const [timeFrom, timeUntil] = startAndEnd(checked);
		if (props.somethingChanged) {
			props.somethingChanged({
				country: selectedCountry?.map((mvc) => mvc.value),
				league: [],
				timeFrom,
				timeUntil,
			});
		}
	}

	function startAndEnd(checked): [Date, Date] {
		if (checked) {
			return [subMinutes(new Date(), 90), new Date()];
		}

		return [timeFrom, timeUntil];
	}

	return (
		<>
			<MatchFilter
				leagues={leagues}
				countries={countries}
				selectedCountry={selectedCountry}
				countryChanged={onChangeCountry}
				currentMatches={currentMatches}
				currentMatchesChanged={onCurrentMatches}
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
	currentMatchesChanged?: (checked: boolean) => void;
};

export type MatchFilterProps = {
	selectedCountry?: PropsValue<SelectOption>;
	countries: SelectOption[];
	leagues: SelectOption[];
	timeFrom?: Date;
	timeUntil?: Date;
	currentMatches?: boolean;
} & MatchFilterHocProps;
