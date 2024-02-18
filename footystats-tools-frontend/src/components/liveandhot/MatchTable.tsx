import React from "react";
import { Table } from "react-bootstrap";
import { LiveAndHotMatches } from "../../footystats-frontendapi";
import { uniqueId } from "lodash";
import translate from "../../i18n/translate";

export const MatchTable = ({ matches }: MatchTableProps) => {
	return (
		<Table striped bordered hover>
			<thead>
				<tr>
					<th>
						{translate("renderer.liveandhot.matchtable.col.start")}
					</th>
					<th>
						{translate("renderer.liveandhot.matchtable.col.home")}
					</th>
					<th>
						{translate("renderer.liveandhot.matchtable.col.away")}
					</th>
					<th>
						{translate(
							"renderer.liveandhot.matchtable.col.country",
						)}
					</th>
					<th>
						{translate(
							"renderer.liveandhot.matchtable.col.hotbets",
						)}
					</th>
				</tr>
			</thead>
			<tbody>
				{matches.map((m) => {
					return (
						<tr key={uniqueId()}>
							<td>{m.start.toLocaleString()}</td>
							<td>{m.homeTeam}</td>
							<td>{m.awayTeam}</td>
							<td>{m.country.countryNameByFootystats}</td>
							<td>
								<ul>
									{Array.from(m.hotBets).map((hb) => {
										return (
											<li key={uniqueId()}>
												{translate(
													`renderer.liveandhot.matchtable.col.hotbets.${hb}`,
												)}
											</li>
										);
									})}
								</ul>
							</td>
						</tr>
					);
				})}
			</tbody>
		</Table>
	);
};

export type MatchTableProps = {
	matches: LiveAndHotMatches[];
};

export default MatchTable;
