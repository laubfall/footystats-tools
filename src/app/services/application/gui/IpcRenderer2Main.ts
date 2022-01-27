import { ipcRenderer, dialog, ipcMain } from 'electron';

export const MSG_OPEN_DIALOG = 'open-directory-dialog';

export function onOpenDialog() {
  ipcMain.handle(MSG_OPEN_DIALOG, () => {
    return dialog.showOpenDialog({
      properties: ['openDirectory'],
    });
  });
}

export function callOpenDialog(setter: (path: string) => void) {
  ipcRenderer
    .invoke(MSG_OPEN_DIALOG)
    .then((value: Electron.OpenDialogReturnValue) => {
      if (value.canceled === false) {
        setter(value.filePaths[0]);
      }
      return null;
    })
    .catch((reason) => console.log(reason));
}

export function ipcRendererInvoke(channel: string, ...args: any) {
  return ipcRenderer
    .invoke(channel, args)
    .catch((reason) => console.log(reason));
}
