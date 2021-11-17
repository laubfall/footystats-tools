import { wait, waitFor } from '@testing-library/dom';
import exp from 'constants';
import fs, { Stats } from 'fs';
import tmp from 'tmp';
import watchImportDirectory from '../../../app/services/application/FileSystemService';

const TMP_DIR_PREFIX = 'FileSystemService.test';

describe('Test low level fs funtions', () => {
  it('Watch tmp directory and file additions and deletions', async () => {
    const testDir = tmp.dirSync({ prefix: TMP_DIR_PREFIX });
    const onChangeMock = jest.fn((path, stats?) => undefined);
    const watcher = watchImportDirectory(testDir.name, onChangeMock);

    try {
      const tmpFile = tmp.fileSync({ dir: testDir.name });
      await new Promise((resolve) => setTimeout(resolve, 20));
      expect(onChangeMock).toHaveBeenCalledTimes(2);
      expect(onChangeMock).toHaveBeenNthCalledWith(
        1,
        'addDir',
        testDir.name,
        expect.anything()
      );

      expect(onChangeMock).toHaveBeenNthCalledWith(
        2,
        'add',
        tmpFile.name,
        expect.anything()
      );
    } finally {
      watcher.close();
      fs.rmSync(testDir.name, { force: true, recursive: true });
    }
  });
});
