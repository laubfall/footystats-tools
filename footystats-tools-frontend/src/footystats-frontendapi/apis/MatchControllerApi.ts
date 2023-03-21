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


import * as runtime from '../runtime';
import type {
  ListMatchRequest,
  PagingResponseMatchListElement,
} from '../models';
import {
    ListMatchRequestFromJSON,
    ListMatchRequestToJSON,
    PagingResponseMatchListElementFromJSON,
    PagingResponseMatchListElementToJSON,
} from '../models';

export interface ListMatchesRequest {
    listMatchRequest: ListMatchRequest;
}

export interface ReimportMatchStatsRequest {
    listMatchRequest: ListMatchRequest;
}

/**
 * 
 */
export class MatchControllerApi extends runtime.BaseAPI {

    /**
     */
    async listMatchesRaw(requestParameters: ListMatchesRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<PagingResponseMatchListElement>> {
        if (requestParameters.listMatchRequest === null || requestParameters.listMatchRequest === undefined) {
            throw new runtime.RequiredError('listMatchRequest','Required parameter requestParameters.listMatchRequest was null or undefined when calling listMatches.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/match/list`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: ListMatchRequestToJSON(requestParameters.listMatchRequest),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => PagingResponseMatchListElementFromJSON(jsonValue));
    }

    /**
     */
    async listMatches(requestParameters: ListMatchesRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<PagingResponseMatchListElement> {
        const response = await this.listMatchesRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async reimportMatchStatsRaw(requestParameters: ReimportMatchStatsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<PagingResponseMatchListElement>> {
        if (requestParameters.listMatchRequest === null || requestParameters.listMatchRequest === undefined) {
            throw new runtime.RequiredError('listMatchRequest','Required parameter requestParameters.listMatchRequest was null or undefined when calling reimportMatchStats.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/match/stats`,
            method: 'PATCH',
            headers: headerParameters,
            query: queryParameters,
            body: ListMatchRequestToJSON(requestParameters.listMatchRequest),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => PagingResponseMatchListElementFromJSON(jsonValue));
    }

    /**
     */
    async reimportMatchStats(requestParameters: ReimportMatchStatsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<PagingResponseMatchListElement> {
        const response = await this.reimportMatchStatsRaw(requestParameters, initOverrides);
        return await response.value();
    }

}
