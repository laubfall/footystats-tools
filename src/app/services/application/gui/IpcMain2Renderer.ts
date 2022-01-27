import { BrowserWindow, ipcRenderer } from 'electron';
import { InvalidConfigurations } from '../../../types/application/Configuration';
import { MainProcessMessageCodes } from '../../../types/application/MessageCodes';

export const MSG_INVALID_CONFIGURATIONS = 'on-invalid-configs';

export const MSG_SIMPLE_MESSAGE = 'on-simple-message';

export type RendererMessage<P> = {
  message?: string;
  channel: string;
  payload: P;
};

export type InvalidConfigurationMessage = RendererMessage<
  InvalidConfigurations[]
>;

export type SimpleMessage = RendererMessage<MainProcessMessageCodes>;

function sendToBrowser<P>(msg: RendererMessage<P>) {
  BrowserWindow.getFocusedWindow()?.webContents.send(msg.channel, msg);
}

export function msgSimpleMessage(msgCode: MainProcessMessageCodes) {
  const msg: SimpleMessage = {
    channel: MSG_SIMPLE_MESSAGE,
    payload: msgCode,
  };

  sendToBrowser(msg);
}

export function msgInvalidConfigurations(ves: InvalidConfigurations[]) {
  const msg: InvalidConfigurationMessage = {
    message: 'Configuration is invalid',
    channel: MSG_INVALID_CONFIGURATIONS,
    payload: ves,
  };

  sendToBrowser(msg);
}

export function subscribeMsgSimpleMessage(onMsg: (msg: SimpleMessage) => void) {
  ipcRenderer.on(MSG_SIMPLE_MESSAGE, (event, args) => {
    onMsg(args);
  });
}

export function subscribeMsgInvalidConfiguration(
  onMsg: (msg: InvalidConfigurationMessage) => void
) {
  ipcRenderer.on(MSG_INVALID_CONFIGURATIONS, (event, args) => {
    onMsg(args);
  });
}

export default {
  msgInvalidConfigurations,
  msgSimpleMessage,
  subscribeMsgInvalidConfiguration,
  subscribeMsgSimpleMessage,
};
