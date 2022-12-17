import { makeAutoObservable } from "mobx";
export class AlertMessages {
	private _messages: string[] = [];
	constructor() {
		makeAutoObservable(this);
	}

	addMessage(msg: string): void {
		this._messages.push(msg);
		setTimeout(() => {
			this._messages = [];
		}, 5000);
	}

	get messages(): string[] {
		return this._messages;
	}
}

export default new AlertMessages();
