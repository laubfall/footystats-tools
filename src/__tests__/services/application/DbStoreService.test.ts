import { DbStoreService } from '../../../app/services/application/DbStoreService';

interface TestDocument {
  value: string;
}

describe('Test Nosql db', () => {
  it('but will it run..', async () => {
    const dbService = new DbStoreService<TestDocument>(
      'C:/Users/Daniel/Desktop/testDocumentNedb.data'
    );
    dbService.insert({ value: '242' });
    const result = await dbService.loadAll();
    expect(result.length).toBeGreaterThan(0);
  });
});
