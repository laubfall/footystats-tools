import React, { useEffect, useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import {
	PaginationChangePage,
	PaginationChangeRowsPerPage,
} from "react-data-table-component/dist/src/DataTable/types";
import { FilterSettings, MatchFilterHoc } from "./MatchFilter";
import { MatchList, MatchListEntry, SortHandler } from "./MatchList";
import IpcMatchService from "../../app/services/match/IpcMatchService";
import {
	BetPredictionQualityBetEnum,
	FootyStatsCsvUploadControllerApi,
	Match,
	MatchControllerApi,
	Paging,
	PagingDirectionEnum,
} from "../../footystats-frontendapi";
import translate from "../../i18n/translate";
import LoadingOverlayStore from "../../mobx/LoadingOverlayStore";
import { apiCatchReasonHandler } from "../functions";
import { utcToZonedTime } from "date-fns-tz";

function matchListEntries(n: Match[]) {
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
					bet: BetPredictionQualityBetEnum.BttsYes,
					prediction: ms.bttsYes,
				},
			],
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

	function createMatchListEntries(n: Match[]) {
		return matchListEntries(n);
	}

	function loadMatches(
		country: string[],
		league: string[],
		from: Date,
		until: Date,
		paging?: Paging,
	) {
		const mss = new IpcMatchService();
		LoadingOverlayStore.loadingNow();
		mss.matchesByFilter(paging, country, league, from, until)
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
			.reimportMatchStats({
				listMatchRequest: {
					country: filter.country,
					league: filter.league,
					start: filter.timeFrom,
					end: filter.timeUntil,
					paging: {
						page: page,
						size: perPage,
						direction: sortOrder,
						properties: [sortColumn],
					},
				},
			})
			.then(async (n) => {
				setTotalRows(n.totalElements);
				const r = await createMatchListEntries(n.elements);
				setMatches(r);
			})
			.catch(apiCatchReasonHandler)
			.finally(() => LoadingOverlayStore.notLoadingNow());
	};

	useEffect(() => {
		loadMatches([], [], undefined, undefined, {
			page: 0,
			size: perPage,
			direction: sortOrder,
			properties: [sortColumn],
		});
	}, []);

	return (
		<>
			<Row>
				<Col md={9}>
					<MatchFilterHoc
						somethingChanged={(changedFilter) => {
							loadMatches(
								changedFilter.country,
								changedFilter.league,
								changedFilter.timeFrom,
								changedFilter.timeUntil,
								{
									page: page,
									size: perPage,
									direction: sortOrder,
									properties: [sortColumn],
								},
							);
							setFilter(changedFilter);
						}}
					/>
				</Col>
				<Col>
					<Button onClick={loadLatestMatchStats}>
						{translate(
							"renderer.matchesview.button.loadmatchstats",
						)}
					</Button>
				</Col>
				<Col>
					<Button onClick={reimportMatchStats}>
						{translate(
							"renderer.matchesview.button.reimportmatchstats",
						)}
					</Button>
				</Col>
			</Row>
			<MatchList
				entries={matches}
				totalRows={totalRows}
				sortHandler={sortHandler}
				pageChange={changePageHandler}
				pageSizeChange={changePageSizeHandler}
				predictionForBets={[
					BetPredictionQualityBetEnum.OverZeroFive,
					BetPredictionQualityBetEnum.BttsYes,
				]}
			/>
		</>
	);
};

export default MatchesView;
