import React, { useState } from "react";
import { SingleValue } from "react-select";
import {
	MatchFilter,
	MatchFilterProps,
	SelectOption,
} from "../../components/matches/MatchFilter";
import "react-datetime-picker/dist/DateTimePicker.css";
import "react-calendar/dist/Calendar.css";
import "react-clock/dist/Clock.css";

export default {
	title: "Matches/Match filter",
};

export const MatchFilterStory = () => {
	const [timeFrom, setTimeFrom] = useState<Date>(new Date());
	const [timeUntil, setTimeUntil] = useState<Date>(new Date());

	const matchFilterProps: MatchFilterProps = {
		countries: [],
		timeFrom,
		timeUntil,
		leagues: [
			{ label: "Bundesliga", value: "Bundesliga" },
			{ label: "Bundesliga 2", value: "Bundesliga2" },
		],
		timeFromChanged: (from) => setTimeFrom(from),
		timeUntilChanged: (until) => setTimeUntil(until),
	};
	return (
		<>
			<span>{timeFrom?.toUTCString()}</span>
			<span>{timeUntil?.toUTCString()}</span>
			<MatchFilter
				leagueChanged={matchFilterProps.leagueChanged}
				leagues={matchFilterProps.leagues}
				timeFrom={matchFilterProps.timeFrom}
				timeUntil={matchFilterProps.timeUntil}
				timeFromChanged={matchFilterProps.timeFromChanged}
				timeUntilChanged={matchFilterProps.timeUntilChanged}
				countries={[]}
			/>
		</>
	);
};
