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
/**
 * 
 * @export
 * @interface Ranking
 */
export interface Ranking {
    /**
     * 
     * @type {number}
     * @memberof Ranking
     */
    position?: number;
    /**
     * 
     * @type {number}
     * @memberof Ranking
     */
    total?: number;
    /**
     * 
     * @type {boolean}
     * @memberof Ranking
     */
    best10Percent?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof Ranking
     */
    best20Percent?: boolean;
}

/**
 * Check if a given object implements the Ranking interface.
 */
export function instanceOfRanking(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function RankingFromJSON(json: any): Ranking {
    return RankingFromJSONTyped(json, false);
}

export function RankingFromJSONTyped(json: any, ignoreDiscriminator: boolean): Ranking {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'position': !exists(json, 'position') ? undefined : json['position'],
        'total': !exists(json, 'total') ? undefined : json['total'],
        'best10Percent': !exists(json, 'best10Percent') ? undefined : json['best10Percent'],
        'best20Percent': !exists(json, 'best20Percent') ? undefined : json['best20Percent'],
    };
}

export function RankingToJSON(value?: Ranking | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'position': value.position,
        'total': value.total,
        'best10Percent': value.best10Percent,
        'best20Percent': value.best20Percent,
    };
}

