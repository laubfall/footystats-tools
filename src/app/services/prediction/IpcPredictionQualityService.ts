import { ipcMain } from 'electron';
import {
  IPredictionQualityService,
  PredictionQualityService,
} from './PredictionQualityService';
import {
  Precast,
  PredictionQualityReport,
  PredictionQualityRevision,
} from './PredictionQualityService.types';
import { ipcRendererInvoke } from '../application/gui/IpcRenderer2Main';

class IpcPredictionQualityService implements IPredictionQualityService {
  computeQuality(): Promise<PredictionQualityReport> {
    return ipcRendererInvoke(
      `predictionQualityService.${this.computeQuality.name}`
    );
  }

  latestRevision(): Promise<PredictionQualityRevision> {
    return ipcRendererInvoke(
      `predictionQualityService.${this.latestRevision.name}`
    );
  }

  precast(revision?: PredictionQualityRevision): Promise<Precast> {
    return ipcRendererInvoke(
      `predictionQualityService.${this.precast.name}`,
      revision
    );
  }

  latestReport(): Promise<PredictionQualityReport> {
    return ipcRendererInvoke(
      `predictionQualityService.${this.latestReport.name}`
    );
  }

  static registerInvokeHandler(configrationService: PredictionQualityService) {
    ipcMain.handle(
      `predictionQualityService.${configrationService.computeQuality.name}`,
      () => configrationService.computeQuality()
    );

    ipcMain.handle(
      `predictionQualityService.${configrationService.latestRevision.name}`,
      () => configrationService.latestRevision()
    );

    ipcMain.handle(
      `predictionQualityService.${configrationService.latestReport.name}`,
      () => configrationService.latestReport()
    );

    ipcMain.handle(
      `predictionQualityService.${configrationService.precast.name}`,
      (...args) => {
        const params = args[1];
        return configrationService.precast(params[0]);
      }
    );
  }
}

export default IpcPredictionQualityService;
