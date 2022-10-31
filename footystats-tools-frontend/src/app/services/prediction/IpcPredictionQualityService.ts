import {
	Precast,
	PredictionQualityReport,
	PredictionQualityRevision,
} from "../../../footystats-frontendapi";
import { RestAPI } from "../../constants";

class IpcPredictionQualityService {
	computeQuality(): Promise<PredictionQualityReport> {
		return RestAPI.predictionQuality.computeQuality();
	}

	latestRevision(): Promise<PredictionQualityRevision> {
		return RestAPI.predictionQuality.latestRevision();
	}

	precast(revision?: PredictionQualityRevision): Promise<Precast> {
		return RestAPI.predictionQuality.precast({
			predictionQualityRevision: revision,
		});
	}

	latestReport(): Promise<PredictionQualityReport> {
		return RestAPI.predictionQuality.latestReport();
	}

	recomputeQuality(
		revision: PredictionQualityRevision,
	): Promise<PredictionQualityReport> {
		return RestAPI.predictionQuality.recomputeQuality({
			predictionQualityRevision: revision,
		});
	}
}

export default IpcPredictionQualityService;
