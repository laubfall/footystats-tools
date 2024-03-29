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
  ExceptionResponse,
  Settings,
} from '../models';
import {
    ExceptionResponseFromJSON,
    ExceptionResponseToJSON,
    SettingsFromJSON,
    SettingsToJSON,
} from '../models';

export interface SaveSettingsRequest {
    settings: Settings;
}

/**
 * 
 */
export class SettingsControllerApi extends runtime.BaseAPI {

    /**
     */
    async loadSettingsRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Settings>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/settings`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => SettingsFromJSON(jsonValue));
    }

    /**
     */
    async loadSettings(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Settings> {
        const response = await this.loadSettingsRaw(initOverrides);
        return await response.value();
    }

    /**
     */
    async saveSettingsRaw(requestParameters: SaveSettingsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<void>> {
        if (requestParameters.settings === null || requestParameters.settings === undefined) {
            throw new runtime.RequiredError('settings','Required parameter requestParameters.settings was null or undefined when calling saveSettings.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/settings`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: SettingsToJSON(requestParameters.settings),
        }, initOverrides);

        return new runtime.VoidApiResponse(response);
    }

    /**
     */
    async saveSettings(requestParameters: SaveSettingsRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<void> {
        await this.saveSettingsRaw(requestParameters, initOverrides);
    }

}
