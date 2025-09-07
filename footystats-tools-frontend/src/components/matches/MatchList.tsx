import React from "react";
import DataTable, { SortOrder, TableColumn } from "react-data-table-component";
import { format } from "date-fns-tz";
import { de } from "date-fns/locale";
import translate from "../../i18n/translate";
import {
	Bet,
	type BetForMatch,
	PredictionResult,
	StatisticalResultOutcome,
} from "../../footystats-frontendapi";
import {
	PaginationChangePage,
	PaginationChangeRowsPerPage,
} from "react-data-table-component/dist/DataTable/types";
import { BetPredictionCell } from "./BetPredictionCell";
import { PlacedBetCell } from "./PlacedBetCell";

function createBetPredictionColumns(predictionForBets?: Bet[]) {
	return (
		predictionForBets?.map((bet) => {
			const tr: TableColumn<MatchListEntry> = {
				name: translate(`renderer.matchesview.bet.${bet}`),
				selector: (row) => {
					const betPrediction = row.betPredictions.find(
						(v) => v.bet === bet,
					);
					return `${betPrediction?.prediction.betSuccessInPercent}`;
				},
				cell: (row) => <BetPredictionCell bet={bet} row={row} />,
				conditionalCellStyles: [
					{
						when: (row) => {
							// completed and betOnThis and correct
							const betPrediction = row.betPredictions.find(
								(v) => v.bet === bet,
							)?.prediction;
							return (
								betPrediction !== undefined &&
								betPrediction.analyzeResult === "SUCCESS"
							);
						},
						style: {
							color: "white",
							backgroundColor: "green",
						},
					},
					{
						when: (row) => {
							// completed and betOnThis and not correct
							const betPrediction = row.betPredictions.find(
								(v) => v.bet === bet,
							)?.prediction;
							return (
								betPrediction !== undefined &&
								betPrediction.analyzeResult === "FAILED"
							);
						},
						style: {
							color: "white",
							backgroundColor: "red",
						},
					},
				],
			};
			return tr;
		}) || []
	);
}

export const MatchList = ({
	entries,
	totalRows,
	sortHandler,
	pageChange,
	pageSizeChange,
	predictionForBets,
}: MatchListProps) => {
	const predictionColumns = createBetPredictionColumns(predictionForBets);

	let columns: TableColumn<MatchListEntry>[] = [
		{
			name: translate("renderer.matchlist.table.col.one"),
			selector: (row) =>
				format(row.gameStartsAt, "E dd.MM.yyyy HH:mm", { locale: de }),
			sortable: true,
			sortField: "dateGMT",
		},
		{
			name: translate("renderer.matchlist.table.col.two"),
			selector: (row) => row.homeTeam,
		},
		{
			name: translate("renderer.matchlist.table.col.three"),
			selector: (row) => row.awayTeam,
		},
		{
			name: translate("renderer.matchlist.table.col.four"),
			selector: (row) => row.country,
		},
		{
			name: translate("renderer.matchlist.table.col.five"),
			selector: (row) => row.result,
		},
		{
			name: translate(""),
			cell: (row) => <PlacedBetCell row={row} />,
		},
	];

	columns = columns.concat(predictionColumns);

	return (
		<DataTable
			columns={columns}
			data={entries}
			onSort={sortHandler}
			onChangePage={pageChange}
			onChangeRowsPerPage={pageSizeChange}
			onRowDoubleClicked={(row) => {
				window.open(
					`https://footystats.org${row.footyStatsUrl}`,
					"_blank",
				);
			}}
			paginationTotalRows={totalRows}
			defaultSortFieldId={1}
			defaultSortAsc={false}
			pagination
			sortServer
			paginationServer
		/>
	);
};

export type BetPrediction = {
	bet: Bet;
	prediction: PredictionResult;
};

export type MatchListEntry = {
	gameStartsAt: Date;
	awayTeam: string;
	homeTeam: string;
	country: string;
	result: string;
	footyStatsUrl: string;
	betPredictions: BetPrediction[];
	statisticalResultOutcome?: StatisticalResultOutcome[];
	placedBets: BetForMatch[];
};

export type MatchListProps = {
	entries: MatchListEntry[];
	totalRows: number;
	sortHandler: SortHandler;
	pageChange?: PaginationChangePage;
	pageSizeChange?: PaginationChangeRowsPerPage;
	predictionForBets?: Bet[];
};

export type SortHandler = {
	(column: TableColumn<MatchListEntry>, sortDirection: SortOrder): void;
};
