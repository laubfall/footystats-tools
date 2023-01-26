import AlertMessagesStore from "../mobx/AlertMessages";
import translate from "../i18n/translate";

export async function apiCatchReasonHandler(reason) {
	const body = await reason.response?.json();
	if (body.EXCEPTION_RESPONSE) {
		const exceptionResponse = body as ExceptionResponse;
		let msg = translate("renderer.api.error.id." + exceptionResponse.id);
		if (msg === undefined || msg === "")
			msg = translate("renderer.api.error.unknown");
		AlertMessagesStore.addMessage(msg);
		return;
	}

	AlertMessagesStore.addMessage(translate("renderer.api.error.unknown"));
}

export type ExceptionResponse = {
	id: string;
};
