import React, { useEffect, useState } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import log from 'electron-log';
import translate from '../../i18n/translate';
import IpcPredictionQualityService from '../../app/services/prediction/IpcPredictionQualityService';
import { ReportList } from './ReportList';
import { PredictionQualityReport } from '../../app/services/prediction/PredictionQualityService.types';

export const PredictionQualityView = () => {
  const [report, setReport] = useState<PredictionQualityReport>();

  const predictionQualityService = new IpcPredictionQualityService();

  useEffect(() => {
    predictionQualityService
      .latestReport()
      .then((rep) => setReport(rep))
      .catch((reason) => log.error('Failed to get latest report', reason));
  }, []);

  return (
    <>
      <Row>
        <Col>
          <Button
            onClick={async () => {
              await predictionQualityService.computeQuality();
              setReport(await predictionQualityService.latestReport());
            }}
          >
            {translate('renderer.predictionqualityview.button.calculate')}
          </Button>
        </Col>
      </Row>
      <ReportList report={report} />
    </>
  );
};

export default { PredictionQualityView };
