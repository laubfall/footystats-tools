import React from "react";
import DataTable, { TableColumn } from "react-data-table-component";
import translate from "../../i18n/translate";
import {
	BetPredictionQualityAllBetsAggregate,
	Report,
} from "../../footystats-frontendapi";

export const ReportList = ({ report, onRowClicked }: ReportListProps) => {
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
			name: translate("renderer.predictionqualitiyview.table.col.three"),
			selector: (bpq) => bpq.betSuccess,
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.four"),
			selector: (bpq) => bpq.betFailed,
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.five"),
			selector: (bpq) => bpq.dontBetSuccess,
		},
		{
			name: translate("renderer.predictionqualitiyview.table.col.six"),
			selector: (bpq) => bpq.dontBetFailed,
		},
	];

	return (
		<>
			<DataTable
				columns={columns}
				data={report?.betPredictionResults || []}
				onRowClicked={onRowClicked}
			/>
		</>
	);
};

export type ReportListProps = {
	report?: Report;
	onRowClicked?: (row: BetPredictionQualityAllBetsAggregate) => void;
};

export default { ReportList };
