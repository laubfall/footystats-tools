import { LeagueStats } from "../../types/stats/LeagueStats";

class IpcLeagueStatsService {
	findeLeagueStatsBy(name: string, season: string): Promise<LeagueStats> {
		return Promise.resolve(undefined);
	}
}

export default IpcLeagueStatsService;
