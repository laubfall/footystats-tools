import fs from 'fs';

export default function watchImportDirectory(
  fileOrDirPatch: string,
  onChange: (event: string, fileName: string) => void
) {
  fs.watch(fileOrDirPatch, undefined, (event, fileName) => {
    onChange(event, fileName);
  });
}
