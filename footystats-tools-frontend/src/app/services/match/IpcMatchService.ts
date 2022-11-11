import { NDate, NString } from "../../types/General";
import { MatchControllerApi, Paging } from "../../../footystats-frontendapi";

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
				start: from,
				end: until,
			},
		});
	}
}

export default IpcMatchService;
