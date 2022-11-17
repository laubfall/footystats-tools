import { TeamStats } from "../../types/stats/TeamStats";

class IpcTeamStatsService {
	latestThree(
		team: string,
		country: string,
		year?: number,
	): Promise<TeamStats[]> {
		return Promise.resolve(undefined);
	}
}

export default IpcTeamStatsService;
