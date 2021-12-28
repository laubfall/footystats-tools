import { ipcMain, OpenDialogReturnValue } from 'electron';
import { ipcRendererInvoke, MSG_OPEN_DIALOG } from '../IpcRenderer2Main';
import INativeGuiService from './INativeGuiService';
import NativeGuiService from './NativeGuiService';

export default class IpcNativeGuiService implements INativeGuiService {
  // eslint-disable-next-line class-methods-use-this
  public callOpenDialog(): Promise<OpenDialogReturnValue> {
    return ipcRendererInvoke(MSG_OPEN_DIALOG);
  }

  public static registerInvokeHandler() {
    ipcMain.handle(MSG_OPEN_DIALOG, () => {
      const ngs = new NativeGuiService();
      return ngs.callOpenDialog();
    });
  }
}
