import log from 'electron-log';
import AsyncNedb from 'nedb-async';
import fs from 'fs';
import cfg from '../../../config';

export class DbStoreService<D> {
  private dbFile: string;

  public readonly DB: AsyncNedb<D>;

  constructor(dbFile: string) {
    this.dbFile = dbFile;
    this.DB = this.loadDb();
  }

  private loadDb(): AsyncNedb<D> {
    const db = new AsyncNedb<D>({
      filename: this.dbFile,
      autoload: true,
      inMemoryOnly: cfg.nedb.inMemoryOnly,
    });
    return db;
  }

  public createUniqueIndex(uniqueProperty: string) {
    this.DB.ensureIndex({ fieldName: uniqueProperty, unique: true });
  }

  public insert(doc: D) {
    this.DB.insert(doc, (err) => {
      if (err != null) {
        log.error(err);
      }
    });
  }

  public insertAll(docs: D[]) {
    docs.forEach((d) => this.insert(d));
  }

  public removeDb() {
    fs.rmSync(this.dbFile);
  }

  public async loadAll(): Promise<D[]> {
    return this.DB.asyncFind<D>({});
  }

  public async asyncFind(
    filter: any,
    cursorModification?: CursorModification[]
  ): Promise<Result<D>> {
    let cursorMods: CursorModifications[][];

    if (cursorModification) {
      cursorMods = [];
      let pagination = false;
      cursorModification?.forEach((cm) => {
        const mod: CursorModifications[] = [];
        mod.push(cm.modification);
        mod.push(cm.parameter);
        cursorMods.push(mod);
        if (cm.modification === 'limit') {
          pagination = true;
        }
      });

      if (pagination) {
        const c = Promise.all([
          this.DB.asyncCount(filter) as Promise<number>,
          this.DB.asyncFind<D>(filter, cursorMods as unknown as D),
        ]);

        return c;
      } // paginate

      return this.DB.asyncFind<D>(filter, cursorMods as unknown as D);
    }
    return this.DB.asyncFind<D>(filter);
  }

  public async asyncFindOne(
    filter: any,
    cursorModification?: CursorModification[]
  ): Promise<D> {
    let cursorMods: CursorModifications[][];
    if (cursorModification) {
      cursorMods = [];
      cursorModification?.forEach((cm) => {
        const mod: CursorModifications[] = [];
        mod.push(cm.modification);
        mod.push(cm.parameter);
        cursorMods.push(mod);
      });
      return this.DB.asyncFindOne<D>(filter, cursorMods as unknown as D);
    }
    return this.DB.asyncFindOne<D>(filter);
  }

  public asyncUpsert(query: any, updateQuery: any) {
    return this.DB.asyncUpdate(query, updateQuery, { upsert: true });
  }
}

export type CursorModificationType = 'sort' | 'skip' | 'limit';

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

type CursorModifications = string | number | SortParameter;

export default DbStoreService;
