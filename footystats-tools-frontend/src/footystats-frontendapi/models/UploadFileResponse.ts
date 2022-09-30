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
 * @interface UploadFileResponse
 */
export interface UploadFileResponse {
    /**
     * 
     * @type {string}
     * @memberof UploadFileResponse
     */
    fileName?: string;
    /**
     * 
     * @type {string}
     * @memberof UploadFileResponse
     */
    fileType?: string;
    /**
     * 
     * @type {number}
     * @memberof UploadFileResponse
     */
    size?: number;
}

export function UploadFileResponseFromJSON(json: any): UploadFileResponse {
    return UploadFileResponseFromJSONTyped(json, false);
}

export function UploadFileResponseFromJSONTyped(json: any, ignoreDiscriminator: boolean): UploadFileResponse {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'fileName': !exists(json, 'fileName') ? undefined : json['fileName'],
        'fileType': !exists(json, 'fileType') ? undefined : json['fileType'],
        'size': !exists(json, 'size') ? undefined : json['size'],
    };
}

export function UploadFileResponseToJSON(value?: UploadFileResponse | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'fileName': value.fileName,
        'fileType': value.fileType,
        'size': value.size,
    };
}

