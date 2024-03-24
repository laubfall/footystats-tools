const substitutionStartTag = "{";

const substitutionEndTag = "}";

let i18n = {};

export default function translate(
	key: string,
	substitutions?: string[],
): string {
	let translation = (<any>i18n)[key];

	if (substitutions) {
		for (let i = 0; i < substitutions.length; i += 1) {
			translation = translation.replace(
				substitutionStartTag + i + substitutionEndTag,
				substitutions[i],
			);
		}
	}

	return translation;
}

export const initialize = async (locale?: Intl.Locale) => {
	switch (locale.language) {
		case "de":
			{
				i18n = await import(`./translations/de.json`);
			}
			break;
		case "en":
			{
				i18n = await import(`./translations/en.json`);
			}
			break;
		default: {
			i18n = await import(`./translations/de.json`);
		}
	}
};
