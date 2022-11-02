import { NDate, NString } from "../../types/General";
import { MatchStats } from "../../types/stats/MatchStats";
import { PredictionResult } from "../prediction/PredictionService.types";
import {
	BetPredictionQualityBetEnum,
	MatchControllerApi,
	Paging,
} from "../../../footystats-frontendapi";

class IpcMatchService {

	async matchesByFilter(
		country: NString,
		league: NString,
		from: NDate,
		until: NDate,
		paging?: Paging,
	) {
		const api = new MatchControllerApi();
		return api.listMatches({
			listMatchRequest: {
				country,
				league,
				paging,
			},
		});
	}
}

export default IpcMatchService;
