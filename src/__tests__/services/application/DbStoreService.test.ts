import {
  Document,
  insert,
  load,
} from '../../../app/services/application/DbStoreService';

class TestDocument extends Document {
  value = '234';
}

describe('Test Nosql db', () => {
  it('but will it run..', async () => {
    insert(new TestDocument());
    const result: TestDocument[] = await load(TestDocument.name);
    expect(result.length).toBeGreaterThan(0);
  });
});
