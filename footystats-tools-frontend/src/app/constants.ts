import { FootyStatsCsvUploadControllerApi, MatchControllerApi, MatchStatsControllerApi, PredictionQualityControllerApi } from "../footystats-frontendapi";

export const INFLUENCER_POINTS = 10;

export const RestAPI = {
	predictionQuality: new PredictionQualityControllerApi(),
	match: new MatchControllerApi(),
	matchStats: new MatchStatsControllerApi(),
	footyStatsCsvUpload: new FootyStatsCsvUploadControllerApi()
};

export default INFLUENCER_POINTS;
