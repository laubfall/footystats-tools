import fs from 'fs';
import tmp from 'tmp';
import watchImportDirectory from '../../../app/services/application/FileSystemService';

const TMP_DIR_PREFIX = 'FileSystemService.test';

describe('Test low level fs funtions', () => {
  it('Watch tmp directory and file additions and deletions', () => {
    const testDir = tmp.dirSync({ prefix: TMP_DIR_PREFIX });

    const onChangeMock = jest.fn((event, fileName) => undefined);

    const watcher = watchImportDirectory(testDir.name, onChangeMock);

    try {
      const tmpFile = tmp.fileSync({ dir: testDir.name });
      expect(onChangeMock).toHaveBeenCalledTimes(1);
      expect(onChangeMock).toHaveBeenCalledWith('chafdsfng2e', tmpFile.name);
    } finally {
      watcher.close();
      fs.rmSync(testDir.name, { force: true, recursive: true });
    }
  });
});
