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
 * @interface InfluencerPercentDistribution
 */
export interface InfluencerPercentDistribution {
    /**
     * 
     * @type {number}
     * @memberof InfluencerPercentDistribution
     */
    predictionPercent?: number;
    /**
     * 
     * @type {number}
     * @memberof InfluencerPercentDistribution
     */
    count?: number;
    /**
     * 
     * @type {string}
     * @memberof InfluencerPercentDistribution
     */
    influencerName?: string;
    /**
     * 
     * @type {string}
     * @memberof InfluencerPercentDistribution
     */
    precheckResult?: InfluencerPercentDistributionPrecheckResultEnum;
}


/**
 * @export
 */
export const InfluencerPercentDistributionPrecheckResultEnum = {
    NotEnoughInformation: 'NOT_ENOUGH_INFORMATION',
    DontKnowWhatToCalculateForBet: 'DONT_KNOW_WHAT_TO_CALCULATE_FOR_BET',
    Exception: 'EXCEPTION',
    Ok: 'OK'
} as const;
export type InfluencerPercentDistributionPrecheckResultEnum = typeof InfluencerPercentDistributionPrecheckResultEnum[keyof typeof InfluencerPercentDistributionPrecheckResultEnum];


/**
 * Check if a given object implements the InfluencerPercentDistribution interface.
 */
export function instanceOfInfluencerPercentDistribution(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function InfluencerPercentDistributionFromJSON(json: any): InfluencerPercentDistribution {
    return InfluencerPercentDistributionFromJSONTyped(json, false);
}

export function InfluencerPercentDistributionFromJSONTyped(json: any, ignoreDiscriminator: boolean): InfluencerPercentDistribution {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'predictionPercent': !exists(json, 'predictionPercent') ? undefined : json['predictionPercent'],
        'count': !exists(json, 'count') ? undefined : json['count'],
        'influencerName': !exists(json, 'influencerName') ? undefined : json['influencerName'],
        'precheckResult': !exists(json, 'precheckResult') ? undefined : json['precheckResult'],
    };
}

export function InfluencerPercentDistributionToJSON(value?: InfluencerPercentDistribution | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'predictionPercent': value.predictionPercent,
        'count': value.count,
        'influencerName': value.influencerName,
        'precheckResult': value.precheckResult,
    };
}
