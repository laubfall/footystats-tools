import { NDate } from "../../types/General";
import { MatchControllerApi, Paging } from "../../../footystats-frontendapi";

class IpcMatchService {
	async matchesByFilter(
		paging: Paging,
		country?: string[],
		league?: string[],
		from?: NDate,
		until?: NDate,
		teamSearchTerms?: string,
	) {
		const api = new MatchControllerApi();
		return api.listMatches({
			listMatchRequest: {
				country,
				league,
				paging,
				start: from,
				end: until,
				fullTextSearchTerms: teamSearchTerms,
			},
		});
	}
}

export default IpcMatchService;
