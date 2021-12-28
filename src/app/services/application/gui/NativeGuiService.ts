import { dialog, ipcMain, OpenDialogReturnValue } from 'electron';
import INativeGuiService, { DialogPathSetter } from './INativeGuiService';

export default class NativeGuiService implements INativeGuiService {
  // eslint-disable-next-line class-methods-use-this
  callOpenDialog(): Promise<OpenDialogReturnValue> {
    return dialog.showOpenDialog({
      properties: ['openDirectory'],
    });
  }
}
