import {
	Precast,
	PredictionQualityReport,
	PredictionQualityRevision,
} from "./PredictionQualityService.types";

class IpcPredictionQualityService {
	computeQuality(): Promise<PredictionQualityReport> {
		return Promise.resolve(undefined);
	}

	latestRevision(): Promise<PredictionQualityRevision> {
		return Promise.resolve(undefined);
	}

	precast(revision?: PredictionQualityRevision): Promise<Precast> {
		return Promise.resolve(undefined);
	}

	latestReport(): Promise<PredictionQualityReport> {
		return Promise.resolve(undefined);
	}

	recomputeQuality(revision: number): Promise<PredictionQualityReport> {
		return Promise.resolve(undefined);
	}
}

export default IpcPredictionQualityService;
