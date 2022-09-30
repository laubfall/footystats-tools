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
    Paging,
    PagingFromJSON,
    PagingFromJSONTyped,
    PagingToJSON,
} from './Paging';

/**
 * 
 * @export
 * @interface ListMatchRequest
 */
export interface ListMatchRequest {
    /**
     * 
     * @type {Date}
     * @memberof ListMatchRequest
     */
    start?: Date;
    /**
     * 
     * @type {string}
     * @memberof ListMatchRequest
     */
    country?: string;
    /**
     * 
     * @type {string}
     * @memberof ListMatchRequest
     */
    league?: string;
    /**
     * 
     * @type {Date}
     * @memberof ListMatchRequest
     */
    end?: Date;
    /**
     * 
     * @type {Paging}
     * @memberof ListMatchRequest
     */
    paging?: Paging;
}

export function ListMatchRequestFromJSON(json: any): ListMatchRequest {
    return ListMatchRequestFromJSONTyped(json, false);
}

export function ListMatchRequestFromJSONTyped(json: any, ignoreDiscriminator: boolean): ListMatchRequest {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'start': !exists(json, 'start') ? undefined : (new Date(json['start'])),
        'country': !exists(json, 'country') ? undefined : json['country'],
        'league': !exists(json, 'league') ? undefined : json['league'],
        'end': !exists(json, 'end') ? undefined : (new Date(json['end'])),
        'paging': !exists(json, 'paging') ? undefined : PagingFromJSON(json['paging']),
    };
}

export function ListMatchRequestToJSON(value?: ListMatchRequest | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'start': value.start === undefined ? undefined : (value.start.toISOString()),
        'country': value.country,
        'league': value.league,
        'end': value.end === undefined ? undefined : (value.end.toISOString()),
        'paging': PagingToJSON(value.paging),
    };
}

