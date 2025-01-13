import { Bet, JobInformation, Report } from "../../../footystats-frontendapi";
import { RestAPI } from "../../constants";

class IpcPredictionQualityService {
	computeQuality(): Promise<JobInformation> {
		return RestAPI.predictionQuality.asyncComputeQuality();
	}

	latestReport(moreQualityDetailsForThisBetType: Bet): Promise<Report> {
		return RestAPI.predictionQuality.latestReport({
			moreQualityDetailsForThisBetType,
		});
	}

	recomputeQuality(): Promise<JobInformation> {
		return RestAPI.predictionQuality.asyncRecomputeQuality();
	}
}

export default IpcPredictionQualityService;
