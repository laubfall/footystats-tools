import chokidar from 'chokidar';
import { Stats } from 'fs';

export default function watchImportDirectory(
  fileOrDirPatch: string,
  onChange: (path: string, stats?: Stats) => void
): chokidar.FSWatcher {
  const watcher = chokidar.watch(fileOrDirPatch);
  watcher.on('add', onChange);
  return watcher;
}
