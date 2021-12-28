import { OpenDialogReturnValue } from 'electron';

export type DialogPathSetter = (path: string) => void;

export default interface INativeGuiService {
  callOpenDialog(): Promise<OpenDialogReturnValue>;
}
