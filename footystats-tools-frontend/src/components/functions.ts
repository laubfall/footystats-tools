import AlertMessagesStore from "../mobx/AlertMessages";
import translate from "../i18n/translate";

export async function apiCatchReasonHandler(reason) {
	const body = await reason.response?.json();
	if (body.EXCEPTION_RESPONSE) {
		const exceptionResponse = body as ExceptionResponse;
		AlertMessagesStore.addMessage(
			translate("renderer.api.error.id." + exceptionResponse.id),
		);
	}
}

export type ExceptionResponse = {
	id: string;
};
