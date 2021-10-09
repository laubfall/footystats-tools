import * as StorageService from '../../app/services/application/StorageService';

jest.mock('../../app/services/application/StorageService');

const storeMock = (StorageService as unknown) as jest.Mocked<
  typeof StorageService
>;

export default storeMock;
