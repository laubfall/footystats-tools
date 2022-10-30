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
import type { InfluencerPercentDistribution } from './InfluencerPercentDistribution';
import {
    InfluencerPercentDistributionFromJSON,
    InfluencerPercentDistributionFromJSONTyped,
    InfluencerPercentDistributionToJSON,
} from './InfluencerPercentDistribution';

/**
 * 
 * @export
 * @interface BetPredictionDistribution
 */
export interface BetPredictionDistribution {
    /**
     * 
     * @type {number}
     * @memberof BetPredictionDistribution
     */
    predictionPercent?: number;
    /**
     * 
     * @type {number}
     * @memberof BetPredictionDistribution
     */
    count?: number;
    /**
     * 
     * @type {Array<InfluencerPercentDistribution>}
     * @memberof BetPredictionDistribution
     */
    influencerDistribution?: Array<InfluencerPercentDistribution>;
}

/**
 * Check if a given object implements the BetPredictionDistribution interface.
 */
export function instanceOfBetPredictionDistribution(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function BetPredictionDistributionFromJSON(json: any): BetPredictionDistribution {
    return BetPredictionDistributionFromJSONTyped(json, false);
}

export function BetPredictionDistributionFromJSONTyped(json: any, ignoreDiscriminator: boolean): BetPredictionDistribution {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'predictionPercent': !exists(json, 'predictionPercent') ? undefined : json['predictionPercent'],
        'count': !exists(json, 'count') ? undefined : json['count'],
        'influencerDistribution': !exists(json, 'influencerDistribution') ? undefined : ((json['influencerDistribution'] as Array<any>).map(InfluencerPercentDistributionFromJSON)),
    };
}

export function BetPredictionDistributionToJSON(value?: BetPredictionDistribution | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'predictionPercent': value.predictionPercent,
        'count': value.count,
        'influencerDistribution': value.influencerDistribution === undefined ? undefined : ((value.influencerDistribution as Array<any>).map(InfluencerPercentDistributionToJSON)),
    };
}

