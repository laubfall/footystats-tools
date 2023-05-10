import {
	JobInformation,
	Precast,
	PredictionQualityRevision,
	Report,
} from "../../../footystats-frontendapi";
import { RestAPI } from "../../constants";
import { BetPredictionQualityBetEnum } from "../../../footystats-frontendapi/models/BetPredictionQuality";

class IpcPredictionQualityService {
	computeQuality(): Promise<JobInformation> {
		return RestAPI.predictionQuality.asyncComputeQuality();
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

	recomputeQuality(): Promise<JobInformation> {
		return RestAPI.predictionQuality.asyncRecomputeQuality();
	}
}

export default IpcPredictionQualityService;
