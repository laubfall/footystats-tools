import { ChangeEvent, DragEvent } from "react";

export type useFileUploadHook = {
  files: File[];
  fileNames: string[];
  fileTypes: string[];
  totalSize: string;
  totalSizeInBytes: number;
  clearAllFiles: () => void;
  createFormData: () => FormData;
  handleDragDropEvent: (e: DragEvent<HTMLElement>) => void;
  removeFile: (file: number | string) => void;
  setFiles: (e: ChangeEvent<HTMLElement> | DragEvent, mode?: "a" | "w") => void;
};
