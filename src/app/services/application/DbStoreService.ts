import AsyncNedb from 'nedb-async';
import fs from 'fs';

export class DbStoreService<D> {
  private dbFile: string;

  private DB: AsyncNedb<D>;

  constructor(dbFile: string) {
    this.dbFile = dbFile;
    this.DB = this.loadDb();
  }

  private loadDb(): AsyncNedb<D> {
    const db = new AsyncNedb<D>({
      filename: this.dbFile,
      autoload: true,
    });
    return db;
  }

  public createUniqueIndex(uniqueProperty: string) {
    this.DB.ensureIndex({ fieldName: uniqueProperty, unique: true });
  }

  public insert(doc: D) {
    this.DB.insert(doc, (err, d) => {
      if (err != null) {
        console.log(err);
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

  public async find(filter: any): Promise<D[]> {
    return this.DB.asyncFind<D>(filter);
  }
}

export default DbStoreService;
