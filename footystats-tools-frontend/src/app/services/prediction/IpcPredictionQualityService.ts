import {
	Precast,
	PredictionQualityRevision,
	Report,
} from "../../../footystats-frontendapi";
import { RestAPI } from "../../constants";
import { BetPredictionQualityBetEnum } from "../../../footystats-frontendapi/models/BetPredictionQuality";

class IpcPredictionQualityService {
	computeQuality(): Promise<void> {
		return RestAPI.predictionQuality.computeQuality();
	}

	precast(revision?: PredictionQualityRevision): Promise<Precast> {
		return RestAPI.predictionQuality.precast({
			predictionQualityRevision: revision,
		});
	}

	latestReport(
		moreQualityDetailsForThisBetType: BetPredictionQualityBetEnum,
	): Promise<Report> {
		return RestAPI.predictionQuality.latestReport({
			moreQualityDetailsForThisBetType,
		});
	}

	recomputeQuality() {
		return RestAPI.predictionQuality.recomputeQuality();
	}
}

export default IpcPredictionQualityService;
