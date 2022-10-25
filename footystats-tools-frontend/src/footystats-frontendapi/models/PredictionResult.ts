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
import {
    InfluencerResult,
    InfluencerResultFromJSON,
    InfluencerResultFromJSONTyped,
    InfluencerResultToJSON,
} from './InfluencerResult';

/**
 * 
 * @export
 * @interface PredictionResult
 */
export interface PredictionResult {
    /**
     * 
     * @type {number}
     * @memberof PredictionResult
     */
    betSuccessInPercent?: number;
    /**
     * 
     * @type {boolean}
     * @memberof PredictionResult
     */
    betOnThis?: boolean;
    /**
     * 
     * @type {string}
     * @memberof PredictionResult
     */
    analyzeResult?: PredictionResultAnalyzeResultEnum;
    /**
     * 
     * @type {Array<InfluencerResult>}
     * @memberof PredictionResult
     */
    influencerDetailedResult?: Array<InfluencerResult>;
}

/**
* @export
* @enum {string}
*/
export enum PredictionResultAnalyzeResultEnum {
    Success = 'SUCCESS',
    Close = 'CLOSE',
    Failed = 'FAILED',
    NotCompleted = 'NOT_COMPLETED',
    NotAnalyzed = 'NOT_ANALYZED',
    NotPredicted = 'NOT_PREDICTED'
}

export function PredictionResultFromJSON(json: any): PredictionResult {
    return PredictionResultFromJSONTyped(json, false);
}

export function PredictionResultFromJSONTyped(json: any, ignoreDiscriminator: boolean): PredictionResult {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'betSuccessInPercent': !exists(json, 'betSuccessInPercent') ? undefined : json['betSuccessInPercent'],
        'betOnThis': !exists(json, 'betOnThis') ? undefined : json['betOnThis'],
        'analyzeResult': !exists(json, 'analyzeResult') ? undefined : json['analyzeResult'],
        'influencerDetailedResult': !exists(json, 'influencerDetailedResult') ? undefined : ((json['influencerDetailedResult'] as Array<any>).map(InfluencerResultFromJSON)),
    };
}

export function PredictionResultToJSON(value?: PredictionResult | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'betSuccessInPercent': value.betSuccessInPercent,
        'betOnThis': value.betOnThis,
        'analyzeResult': value.analyzeResult,
        'influencerDetailedResult': value.influencerDetailedResult === undefined ? undefined : ((value.influencerDetailedResult as Array<any>).map(InfluencerResultToJSON)),
    };
}
