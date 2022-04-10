import * as i18n from './de.json';

export default function translate(key: string): string {
  return (<any>i18n)[key];
}
