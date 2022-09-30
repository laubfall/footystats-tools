/**
 * Codes for messages that are result of some processing inside the main process.
 *
 * These codes can be used to identify computations in main process and are most
 * likely evaluated by frontend to show some user friendly message.
 */
export enum MainProcessMessageCodes {
  STARTED_IMPORT_DIRECTORY_WATCH,
  MATCH_FILE_IMPORTED,
}

export default {
  MainProcessMessageCodes,
};
