import { makeAutoObservable } from "mobx";

export class LoadingOverlayStore {
	private _loading = false;

	constructor() {
		makeAutoObservable(this);
	}

	loadingNow(): void {
		this._loading = true;
	}

	notLoadingNow(): void {
		this._loading = false;
	}

	get loading(): boolean {
		return this._loading;
	}
}

export default new LoadingOverlayStore();
