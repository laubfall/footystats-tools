/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
import type { Password } from './Password';
import {
    PasswordFromJSON,
    PasswordFromJSONTyped,
    PasswordToJSON,
} from './Password';

/**
 * 
 * @export
 * @interface Settings
 */
export interface Settings {
    /**
     * 
     * @type {string}
     * @memberof Settings
     */
    getId?: string;
    /**
     * 
     * @type {string}
     * @memberof Settings
     */
    footyStatsUsername?: string;
    /**
     * 
     * @type {Password}
     * @memberof Settings
     */
    footyStatsPassword?: Password;
}

/**
 * Check if a given object implements the Settings interface.
 */
export function instanceOfSettings(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function SettingsFromJSON(json: any): Settings {
    return SettingsFromJSONTyped(json, false);
}

export function SettingsFromJSONTyped(json: any, ignoreDiscriminator: boolean): Settings {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'getId': !exists(json, 'get_id') ? undefined : json['get_id'],
        'footyStatsUsername': !exists(json, 'footyStatsUsername') ? undefined : json['footyStatsUsername'],
        'footyStatsPassword': !exists(json, 'footyStatsPassword') ? undefined : PasswordFromJSON(json['footyStatsPassword']),
    };
}

export function SettingsToJSON(value?: Settings | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'get_id': value.getId,
        'footyStatsUsername': value.footyStatsUsername,
        'footyStatsPassword': PasswordToJSON(value.footyStatsPassword),
    };
}

