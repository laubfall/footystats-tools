import fs from 'fs';

export default function watchImportDirectory(
  fileOrDirPatch: string,
  onChange: (event: string, fileName: string) => void
): fs.FSWatcher {
  return fs.watch(fileOrDirPatch, undefined, onChange);
}
