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
 * @interface JobInformation
 */
export interface JobInformation {
    /**
     * 
     * @type {number}
     * @memberof JobInformation
     */
    jobId?: number;
    /**
     * 
     * @type {string}
     * @memberof JobInformation
     */
    job?: JobInformationJobEnum;
    /**
     * 
     * @type {number}
     * @memberof JobInformation
     */
    progressInPercent?: number;
    /**
     * 
     * @type {number}
     * @memberof JobInformation
     */
    itemsToProcess?: number;
    /**
     * 
     * @type {number}
     * @memberof JobInformation
     */
    currentReadCount?: number;
    /**
     * 
     * @type {string}
     * @memberof JobInformation
     */
    jobName?: string;
}


/**
 * @export
 */
export const JobInformationJobEnum = {
    Running: 'RUNNING',
    Completed: 'COMPLETED'
} as const;
export type JobInformationJobEnum = typeof JobInformationJobEnum[keyof typeof JobInformationJobEnum];


/**
 * Check if a given object implements the JobInformation interface.
 */
export function instanceOfJobInformation(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function JobInformationFromJSON(json: any): JobInformation {
    return JobInformationFromJSONTyped(json, false);
}

export function JobInformationFromJSONTyped(json: any, ignoreDiscriminator: boolean): JobInformation {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'jobId': !exists(json, 'jobId') ? undefined : json['jobId'],
        'job': !exists(json, 'job') ? undefined : json['job'],
        'progressInPercent': !exists(json, 'progressInPercent') ? undefined : json['progressInPercent'],
        'itemsToProcess': !exists(json, 'itemsToProcess') ? undefined : json['itemsToProcess'],
        'currentReadCount': !exists(json, 'currentReadCount') ? undefined : json['currentReadCount'],
        'jobName': !exists(json, 'jobName') ? undefined : json['jobName'],
    };
}

export function JobInformationToJSON(value?: JobInformation | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'jobId': value.jobId,
        'job': value.job,
        'progressInPercent': value.progressInPercent,
        'itemsToProcess': value.itemsToProcess,
        'currentReadCount': value.currentReadCount,
        'jobName': value.jobName,
    };
}

