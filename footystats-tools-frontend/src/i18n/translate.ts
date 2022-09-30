import * as i18n from './de.json';

const substitutionStartTag = '{';

const substitutionEndTag = '}';

export default function translate(
	key: string,
	substitutions?: string[]
): string {
	let translation = (<any>i18n)[key];

	if (substitutions) {
		for (let i = 0; i < substitutions.length; i += 1) {
			translation = translation.replace(
				substitutionStartTag + i + substitutionEndTag,
				substitutions[i]
			);
		}
	}

	return translation;
}
