import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import {
	PaginationChangePage,
	PaginationChangeRowsPerPage,
} from "react-data-table-component/dist/src/DataTable/types";
import { FilterSettings, MatchFilterHoc } from "./MatchFilter";
import { MatchList, MatchListEntry, SortHandler } from "./MatchList";
import IpcMatchService from "../../app/services/match/IpcMatchService";
import {
	FootyStatsCsvUploadControllerApi,
	MatchControllerApi,
	MatchListElement,
	Paging,
	PagingDirectionEnum,
} from "../../footystats-frontendapi";
import translate from "../../i18n/translate";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";
import { apiCatchReasonHandler } from "../functions";
import { utcToZonedTime } from "date-fns-tz";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";
import JobProgressStore from "../../mobx/JobProgressStore";
import { useDebouncedEffect } from "../../react/useDebounce-hook";

function matchListEntries(n: MatchListElement[]) {
	const r = n.map(async (ms) => {
		const mle: MatchListEntry = {
			gameStartsAt: utcToZonedTime(
				new Date(ms.dateGMT.toJSON()),
				"Europe/Berlin",
			),
			awayTeam: ms.awayTeam,
			homeTeam: ms.homeTeam,
			country: ms.country,
			result:
				ms.state === "complete"
					? `
        ${ms.goalsHomeTeam} : ${ms.goalsAwayTeam}`
					: "-",
			footyStatsUrl: ms.footyStatsUrl,
			betPredictions: [
				{
					bet: BetPredictionQualityBetEnum.OverZeroFive,
					prediction: ms.o05,
				},
				{
					bet: BetPredictionQualityBetEnum.OverOneFive,
					prediction: ms.o15,
				},
				{
					bet: BetPredictionQualityBetEnum.BttsYes,
					prediction: ms.bttsYes,
				},
			],
			statisticalResultOutcome: ms.statisticalResultOutcome,
		};

		return mle;
	});

	return Promise.all(r);
}

export const MatchesView = () => {
	const [matches, setMatches] = useState<MatchListEntry[]>([]);
	const [totalRows, setTotalRows] = useState<number>(0);
	const [perPage, setPerPage] = useState(10);
	const [page, setPage] = useState(0);
	const [sortColumn, setSortColumn] = useState<string | undefined>("dateGMT");
	const [sortOrder, setSortOrder] = useState<PagingDirectionEnum>(
		PagingDirectionEnum.Desc,
	);
	const [filter, setFilter] = useState<FilterSettings>({
		country: [],
		league: [],
		timeFrom: undefined,
		timeUntil: undefined,
	});

	function createMatchListEntries(n: MatchListElement[]) {
		return matchListEntries(n);
	}

	function loadMatches(
		country: string[],
		league: string[],
		from: Date,
		until: Date,
		teamSearchTerms?: string,
		paging?: Paging,
	) {
		const mss = new IpcMatchService();
		LoadingOverlayStore.loadingNow();
		mss.matchesByFilter(
			paging,
			country,
			league,
			from,
			until,
			teamSearchTerms,
		)
			.then(async (n) => {
				setTotalRows(n.totalElements);
				const r = await createMatchListEntries(n.elements);
				setMatches(r);
			})
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	}

	const sortHandler: SortHandler = (column, newSortOrder) => {
		const sortThatWay =
			newSortOrder === "asc"
				? PagingDirectionEnum.Asc
				: PagingDirectionEnum.Desc;
		setSortOrder(sortThatWay);
		setSortColumn(column.sortField);
		loadMatches(
			filter.country,
			filter.league,
			filter.timeFrom,
			filter.timeUntil,
			filter.teamSearch,
			{
				page: page,
				size: perPage,
				direction: sortThatWay,
				properties: [column.sortField],
			},
		);
	};

	const changePageHandler: PaginationChangePage = (newPage) => {
		const newPageValue = newPage - 1;
		setPage(newPageValue);
		loadMatches(
			filter.country,
			filter.league,
			filter.timeFrom,
			filter.timeUntil,
			filter.teamSearch,
			{
				page: newPageValue,
				size: perPage,
				direction: sortOrder,
				properties: [sortColumn],
			},
		);
	};

	const changePageSizeHandler: PaginationChangeRowsPerPage = (
		newPerPage,
		currentPage,
	) => {
		loadMatches(
			filter.country,
			filter.league,
			filter.timeFrom,
			filter.timeUntil,
			filter.teamSearch,
			{
				page: currentPage - 1,
				size: newPerPage,
				direction: sortOrder,
				properties: [sortColumn],
			},
		);
		setPerPage(newPerPage);
	};
	const loadLatestMatchStats = () => {
		const footyStatsApi = new FootyStatsCsvUploadControllerApi();
		LoadingOverlayStore.loadingNow(
			translate("renderer.matchesview.overlay.loading.footystats"),
		);
		footyStatsApi
			.loadMatchesOfTheDayFromFooty()
			.then(() =>
				loadMatches(
					filter.country,
					filter.league,
					filter.timeFrom,
					filter.timeUntil,
					filter.teamSearch,
					{
						page: page,
						size: perPage,
						direction: sortOrder,
						properties: [sortColumn],
					},
				),
			)
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	};

	const reimportMatchStats = () => {
		const matchControllerApi = new MatchControllerApi();
		LoadingOverlayStore.loadingNow(
			translate("renderer.matchesview.overlay.loading.footystats"),
		);

		matchControllerApi
			.reimportMatchStats()
			.then((jobInformation) => JobProgressStore.addJob(jobInformation))
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	};

	useEffect(() => {
		loadMatches([], [], undefined, undefined, undefined, {
			page: 0,
			size: perPage,
			direction: sortOrder,
			properties: [sortColumn],
		});
	}, []);

	useDebouncedEffect(
		() => {
			loadMatches(
				filter.country,
				filter.league,
				filter.timeFrom,
				filter.timeUntil,
				filter.teamSearch,
				{
					page: page,
					size: perPage,
					direction: sortOrder,
					properties: [sortColumn],
				},
			);
		},
		[filter],
		500,
	);

	return (
		<>
			<MatchFilterHoc somethingChanged={setFilter} />

			<MatchList
				entries={matches}
				totalRows={totalRows}
				sortHandler={sortHandler}
				pageChange={changePageHandler}
				pageSizeChange={changePageSizeHandler}
				predictionForBets={[
					BetPredictionQualityBetEnum.OverZeroFive,
					BetPredictionQualityBetEnum.OverOneFive,
					BetPredictionQualityBetEnum.BttsYes,
				]}
			/>
			<div className={"m-2 d-flex justify-content-end"}>
				<div className={"me-2"}>
					<Button onClick={loadLatestMatchStats}>
						{translate(
							"renderer.matchesview.button.loadmatchstats",
						)}
					</Button>
				</div>
				<div>
					<Button onClick={reimportMatchStats}>
						{translate(
							"renderer.matchesview.button.reimportmatchstats",
						)}
					</Button>
				</div>
			</div>
		</>
	);
};

export default MatchesView;
