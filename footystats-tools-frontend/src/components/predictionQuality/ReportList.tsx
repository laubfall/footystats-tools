import React from "react";
import DataTable, { TableColumn } from "react-data-table-component";
import translate from "../../i18n/translate";
import {
	BetPredictionQualityAllBetsAggregate,
	Report,
} from "../../footystats-frontendapi";
import { BetPredictionQualityBetEnum } from "../../footystats-frontendapi/models/BetPredictionQuality";

export const ReportList = ({
	report,
	onRowClicked,
	selectedBet,
}: ReportListProps) => {
	function percentDisplay(value: number, total: number) {
		return `${value} (${Math.round((value / total) * 100)}%)`;
	}

	const columns: TableColumn<BetPredictionQualityAllBetsAggregate>[] = [
		{
			name: translate("renderer.predictionqualitiyview.table.col.one"),
			selector: (bpq) =>
				translate(
					`renderer.predictionqualitiyview.table.col.one.${bpq.bet}`,
				),
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.two"),
			selector: (bpq) => bpq.assessed,
		},
		{
			name: translate(
				"renderer.predictionqualitiyview.table.col.betsuccess",
			),
			selector: (bpq) => {
				const sum = bpq.betSuccess + bpq.dontBetSuccess;
				return percentDisplay(sum, bpq.assessed);
			},
		},
		{
			name: translate(
				"renderer.predictionqualitiyview.table.col.betfailed",
			),
			selector: (bpq) => {
				const sum = bpq.betFailed + bpq.dontBetFailed;
				return percentDisplay(sum, bpq.assessed);
			},
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.three"),
			selector: (bpq) =>
				percentDisplay(bpq.betSuccess, bpq.betFailed + bpq.betSuccess),
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.four"),
			selector: (bpq) =>
				percentDisplay(bpq.betFailed, bpq.betFailed + bpq.betSuccess),
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.five"),
			selector: (bpq) =>
				percentDisplay(
					bpq.dontBetSuccess,
					bpq.dontBetFailed + bpq.dontBetSuccess,
				),
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.six"),
			selector: (bpq) =>
				percentDisplay(
					bpq.dontBetFailed,
					bpq.dontBetFailed + bpq.dontBetSuccess,
				),
		},
	];

	return (
		<>
			<DataTable
				columns={columns}
				data={report?.betPredictionResults || []}
				onRowClicked={onRowClicked}
				conditionalRowStyles={[
					{
						when: (row) => row.bet === selectedBet,
						style: {
							backgroundColor: "green",
							color: "white",
						},
					},
				]}
			/>
		</>
	);
};

export type ReportListProps = {
	report?: Report;
	onRowClicked?: (row: BetPredictionQualityAllBetsAggregate) => void;
	selectedBet?: BetPredictionQualityBetEnum;
};

export default { ReportList };
