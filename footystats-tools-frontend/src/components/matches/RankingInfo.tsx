import React from "react";
import { Ranking } from "../../footystats-frontendapi";
import translate from "../../i18n/translate";
import { BsAirplaneFill, BsArrowUpCircleFill } from "react-icons/bs";

export const RankingInfo = ({ ranking }: RankingInfoProps) => {
	function influencerRanking(ranking: Ranking): string {
		if (!ranking) {
			return "";
		}

		return `${translate("renderer.matchlist.influencer.popup.ranking")} ${
			ranking.position
		}/${ranking.total}`;
	}

	return (
		<div id={"rankingInfo"}>
			<div className={"col-12"}>
				{influencerRanking(ranking)}
				{ranking?.best10Percent && <BsAirplaneFill />}
				{!ranking?.best10Percent && ranking?.best20Percent && (
					<BsArrowUpCircleFill />
				)}
			</div>
		</div>
	);
};

export type RankingInfoProps = {
	ranking: Ranking;
};
