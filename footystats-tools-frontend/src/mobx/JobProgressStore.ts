import { makeAutoObservable } from "mobx";
import {
	JobInformation,
	JobInformationControllerApi,
} from "../footystats-frontendapi";

export class JobProgressStore {
	private currentJobs: JobInformation[] = [];

	private intervalId = null;

	constructor() {
		makeAutoObservable(this);
		this.createInterval();
	}

	get jobs(): JobInformation[] {
		return this.currentJobs;
	}

	finished(jobId: number): void {
		if (!jobId) {
			return;
		}
		this.currentJobs = this.currentJobs.filter(
			(job) => job.jobId !== jobId,
		);

		if (this.currentJobs.length === 0) {
			clearInterval(this.intervalId);
			this.intervalId = null;
		}
	}

	addJob(job: JobInformation): void {
		this.currentJobs.push(job);
		if (this.intervalId === null) {
			this.createInterval();
		}
	}

	// private function that changes progressInPercent of a specific job
	private changeProgress(jobInfo: JobInformation): void {
		for (const job of this.jobs) {
			if (job.jobId === jobInfo.jobId) {
				job.progressInPercent = jobInfo.progressInPercent;
				job.currentReadCount = jobInfo.currentReadCount;
				job.itemsToProcess = jobInfo.itemsToProcess;
			}
		}
	}

	private createInterval() {
		if (this.intervalId !== null) {
			clearInterval(this.intervalId);
		}
		this.intervalId = setInterval(() => {
			new JobInformationControllerApi()
				.allRunning()
				.then((jobs) => {
					if (jobs.length === 0) {
						clearInterval(this.intervalId);
						this.intervalId = null;
					}

					jobs.filter((job) => job.job === "COMPLETED").forEach(
						(job) => this.finished(job.jobId),
					);

					jobs.filter((job) => job.job === "RUNNING").forEach(
						(job) => {
							for (const j of this.jobs) {
								if (j.jobId === job.jobId) {
									this.changeProgress(job);
								}
							}
						},
					);
				})
				.catch((err) =>
					console.log("Failed to retrieve job status", err),
				);
		}, 800);
	}
}

export default new JobProgressStore();
