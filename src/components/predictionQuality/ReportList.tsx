import React from 'react';
import DataTable, { TableColumn } from 'react-data-table-component';
import {
  BetPredictionQuality,
  PredictionQualityReport,
} from '../../app/services/prediction/PredictionQualityService.types';
import translate from '../../i18n/translate';

export const ReportList = ({ report }: ReportListProps) => {
  const columns: TableColumn<BetPredictionQuality>[] = [
    {
      name: translate('renderer.predictionqualitiyview.table.col.one'),
      selector: (bpq) =>
        translate(`renderer.predictionqualitiyview.table.col.one.${bpq.bet}`),
    },
    {
      name: translate('renderer.predictionqualitiyview.table.col.two'),
      selector: (bpq) => bpq.countAssessed,
    },
    {
      name: translate('renderer.predictionqualitiyview.table.col.three'),
      selector: (bpq) => bpq.countSuccess,
    },
    {
      name: translate('renderer.predictionqualitiyview.table.col.four'),
      selector: (bpq) => bpq.countFailed,
    },
    {
      name: translate('renderer.predictionqualitiyview.table.col.five'),
      selector: (bpq) => bpq.countSuccessDontBet,
    },
    {
      name: translate('renderer.predictionqualitiyview.table.col.six'),
      selector: (bpq) => bpq.countFailedDontBet,
    },
  ];

  return (
    <>
      <DataTable columns={columns} data={report?.measurements || []} />
    </>
  );
};

export type ReportListProps = {
  report?: PredictionQualityReport;
};

export default { ReportList };
