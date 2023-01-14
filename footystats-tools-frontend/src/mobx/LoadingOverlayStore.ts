import { makeAutoObservable } from "mobx";

export class LoadingOverlayStore {
	private _loading = false;

	private _loadingMessage = "";

	constructor() {
		makeAutoObservable(this);
	}

	loadingNow(loadingMessage?: string): void {
		this._loading = true;
		if (loadingMessage) {
			this._loadingMessage = loadingMessage;
		}
	}

	notLoadingNow(): void {
		this._loading = false;
		this._loadingMessage = undefined;
	}

	get loading(): boolean {
		return this._loading;
	}

	get loadingMessage(): string {
		return this._loadingMessage;
	}
}

export default new LoadingOverlayStore();
