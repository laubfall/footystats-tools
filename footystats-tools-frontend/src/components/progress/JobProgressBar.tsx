import React from "react";
import { ListGroup, ListGroupItem, ProgressBar } from "react-bootstrap";
import { observer } from "mobx-react-lite";
import JobProgressStore from "../../mobx/JobProgressStore";
import {
	JobInformation,
	JobInformationJobEnum,
} from "../../footystats-frontendapi";
import translate from "../../i18n/translate";

export const JobProgressBar = ({
	progressInPercent,
	processedItems,
	totalItemsToProcess,
	jobName,
	status,
}: JobProgressBarProps) => {
	if (status === "COMPLETED") {
		return null;
	}

	// assign jobName to a new variable followed by a double colon, if jobName is undefined assign an empty string
	let label = jobName ? `${jobName}: ` : "";
	// if totalItemsToProcess and processedItems are defined, append these values to the label variable in brackets delimited by a slash.
	if (totalItemsToProcess !== undefined && processedItems !== undefined) {
		label += `(${processedItems}/${totalItemsToProcess})`;
	}

	return (
		<ProgressBar
			now={progressInPercent}
			label={`${progressInPercent} ${label}`}
		/>
	);
};

export type JobProgressBarProps = {
	jobName?: string;
	progressInPercent: number;
	totalItemsToProcess?: number;
	processedItems?: number;
	status: JobInformationJobEnum;
};

export const ObsJobProgressBar = observer(
	({ jobs = [...JobProgressStore.jobs] }: any) => {
		return (
			<>
				{jobs.length > 0 && (
					<>
						<h3>{translate("renderer.progress.job.title")}</h3>
						<ListGroup>
							{jobs.map((job: JobInformation) => (
								<ListGroupItem key={job.jobId}>
									<span>
										{translate(
											`renderer.progress.job.${job.jobName}`,
										)}
									</span>
									<JobProgressBar
										key={job.jobId}
										processedItems={job.currentReadCount}
										totalItemsToProcess={job.itemsToProcess}
										progressInPercent={
											job.progressInPercent
										}
										status={job.job}
									/>
								</ListGroupItem>
							))}
						</ListGroup>
					</>
				)}
			</>
		);
	},
);

export default JobProgressBar;
