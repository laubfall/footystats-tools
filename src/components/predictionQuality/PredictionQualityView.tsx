import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import log from 'electron-log';
import translate from '../../i18n/translate';
import IpcPredictionQualityService from '../../app/services/prediction/IpcPredictionQualityService';
import { ReportList } from './ReportList';
import {
  NO_REVISION_SO_FAR,
  PredictionQualityReport,
} from '../../app/services/prediction/PredictionQualityService.types';

export const PredictionQualityView = () => {
  const [report, setReport] = useState<PredictionQualityReport>();

  const [recalculateAvailable, setRecalculateAvailable] = useState(false);

  const predictionQualityService = new IpcPredictionQualityService();

  useEffect(() => {
    predictionQualityService
      .latestReport()
      .then((rep) => setReport(rep))
      .catch((reason) => log.error('Failed to get latest report', reason));

    predictionQualityService
      .latestRevision()
      .then((rev) => setRecalculateAvailable(rev !== NO_REVISION_SO_FAR))
      .catch((reason) =>
        log.error('Failed to compute state for recalculate button', reason)
      );
  }, []);

  return (
    <>
      <Row>
        <Col>
          <Button
            onClick={async () => {
              setReport(await predictionQualityService.computeQuality());
            }}
          >
            {translate('renderer.predictionqualityview.button.calculate')}
          </Button>
          <Button
            disabled={recalculateAvailable === false}
            onClick={async () => {
              const lr = await predictionQualityService.latestRevision();
              setReport(await predictionQualityService.recomputeQuality(lr));
            }}
          >
            {translate('renderer.predictionqualityview.button.recalculate')}
          </Button>
        </Col>
      </Row>
      <ReportList report={report} />
    </>
  );
};

export default { PredictionQualityView };
