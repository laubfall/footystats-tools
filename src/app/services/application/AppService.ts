/**
 * Functions that refelects the application behavior.
 *
 * This is typically the steps that are needed to make in order
 * to provide user functionality.
 */

import { Country } from '../../types/application/AppControll';

/**
 * Application provide countries based on the already imported footystat csv files.
 * Services that read footystats csv files check if imported data contains information
 * about countries and add the country to the app store (if it does not exist already).
 * @param name Mandatory. Name of the country. Method check case insensitive if the country
 * exists inside the app store.
 */
export function addAvailableCountry(name: string) {}

export function availableCountries(): Country[] {
  return [];
}

export default function startApplication() {
  // TODO
}
