export type NString = string | null;
export type NDate = Date | null;
export type NNumber = number | null;

export type CursorModificationType = "sort" | "skip" | "limit";

export type SortOrder = 1 | -1;

export type SortParameter = {
  [key: string]: SortOrder;
};

export type CursorModification = {
  modification: CursorModificationType;
  parameter: number | SortParameter;
};

export type PagedResult<JSON> = [number, Array<JSON>];

export type Result<JSON> = Array<JSON> | PagedResult<JSON>;

export type CursorModifications = string | number | SortParameter;