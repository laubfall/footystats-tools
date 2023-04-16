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
 * @interface BetPredictionQualityAllBetsAggregate
 */
export interface BetPredictionQualityAllBetsAggregate {
    /**
     * 
     * @type {string}
     * @memberof BetPredictionQualityAllBetsAggregate
     */
    bet?: BetPredictionQualityAllBetsAggregateBetEnum;
    /**
     * 
     * @type {number}
     * @memberof BetPredictionQualityAllBetsAggregate
     */
    assessed?: number;
    /**
     * 
     * @type {number}
     * @memberof BetPredictionQualityAllBetsAggregate
     */
    betSuccess?: number;
    /**
     * 
     * @type {number}
     * @memberof BetPredictionQualityAllBetsAggregate
     */
    betFailed?: number;
    /**
     * 
     * @type {number}
     * @memberof BetPredictionQualityAllBetsAggregate
     */
    dontBetSuccess?: number;
    /**
     * 
     * @type {number}
     * @memberof BetPredictionQualityAllBetsAggregate
     */
    dontBetFailed?: number;
}


/**
 * @export
 */
export const BetPredictionQualityAllBetsAggregateBetEnum = {
    OverZeroFive: 'OVER_ZERO_FIVE',
    OverOneFive: 'OVER_ONE_FIVE',
    BttsYes: 'BTTS_YES',
    BttsNo: 'BTTS_NO'
} as const;
export type BetPredictionQualityAllBetsAggregateBetEnum = typeof BetPredictionQualityAllBetsAggregateBetEnum[keyof typeof BetPredictionQualityAllBetsAggregateBetEnum];


/**
 * Check if a given object implements the BetPredictionQualityAllBetsAggregate interface.
 */
export function instanceOfBetPredictionQualityAllBetsAggregate(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function BetPredictionQualityAllBetsAggregateFromJSON(json: any): BetPredictionQualityAllBetsAggregate {
    return BetPredictionQualityAllBetsAggregateFromJSONTyped(json, false);
}

export function BetPredictionQualityAllBetsAggregateFromJSONTyped(json: any, ignoreDiscriminator: boolean): BetPredictionQualityAllBetsAggregate {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'bet': !exists(json, 'bet') ? undefined : json['bet'],
        'assessed': !exists(json, 'assessed') ? undefined : json['assessed'],
        'betSuccess': !exists(json, 'betSuccess') ? undefined : json['betSuccess'],
        'betFailed': !exists(json, 'betFailed') ? undefined : json['betFailed'],
        'dontBetSuccess': !exists(json, 'dontBetSuccess') ? undefined : json['dontBetSuccess'],
        'dontBetFailed': !exists(json, 'dontBetFailed') ? undefined : json['dontBetFailed'],
    };
}

export function BetPredictionQualityAllBetsAggregateToJSON(value?: BetPredictionQualityAllBetsAggregate | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'bet': value.bet,
        'assessed': value.assessed,
        'betSuccess': value.betSuccess,
        'betFailed': value.betFailed,
        'dontBetSuccess': value.dontBetSuccess,
        'dontBetFailed': value.dontBetFailed,
    };
}

