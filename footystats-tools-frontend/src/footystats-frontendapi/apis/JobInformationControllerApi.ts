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
  JobInformation,
} from '../models';
import {
    JobInformationFromJSON,
    JobInformationToJSON,
} from '../models';

export interface RunningRequest {
    jobName: string;
}

/**
 * 
 */
export class JobInformationControllerApi extends runtime.BaseAPI {

    /**
     */
    async runningRaw(requestParameters: RunningRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<JobInformation>> {
        if (requestParameters.jobName === null || requestParameters.jobName === undefined) {
            throw new runtime.RequiredError('jobName','Required parameter requestParameters.jobName was null or undefined when calling running.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/running`.replace(`{${"jobName"}}`, encodeURIComponent(String(requestParameters.jobName))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => JobInformationFromJSON(jsonValue));
    }

    /**
     */
    async running(requestParameters: RunningRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<JobInformation> {
        const response = await this.runningRaw(requestParameters, initOverrides);
        return await response.value();
    }

}