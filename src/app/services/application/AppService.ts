/**
 * Functions that refelects the application behavior.
 *
 * This is typically the steps that are needed to make in order
 * to provide user functionality.
 */
function accessStorage() {
  const isRenderer = process && process.type === 'renderer';
  if (isRenderer === true) {
    return window.localStorage;
  }
  // eslint-disable-next-line global-require
  const { localStorage } = require('electron-browser-storage');
  return localStorage;
}

const as = accessStorage();
function loadStats() {
  as.setItem('stats', 'hello_stats5');
}

export function tmpStats(): string {
  return as.getItem('stats');
}

export default function startApplication() {
  loadStats();
}
