function accessStorage(): Storage {
  const isRenderer = process && process.type === 'renderer';
  if (isRenderer === true) {
    return window.localStorage;
  }
  // eslint-disable-next-line global-require
  const { localStorage } = require('electron-browser-storage');
  return localStorage;
}

export function store(key: string, obj: string) {
  const as: Storage = accessStorage();
  as.setItem(key, obj);
}

export function load(key: string) {
  const as = accessStorage();
  return as.getItem(key);
}
