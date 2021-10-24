import AsyncNedb from 'nedb-async';
import config from '../../../config';

export interface Document {
  type: string;
}

function loadDb(): AsyncNedb<unknown> {
  const db = new AsyncNedb({
    filename: `${config.db}/nedb.data`,
    autoload: true,
  });
  return db;
}

const DB: AsyncNedb<any> = loadDb();

export function insert(doc: Document) {
  const type = (doc as any).constructor.name;
  doc.type = type;
  DB.insert(doc);
}

export function load(typeName: string) {
  return DB.asyncFind({ type: typeName });
}

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

  public insert(doc: D) {
    this.DB.insert(doc, (err, d) => console.log(err));
  }

  public insertAll(docs: D[]) {
    docs.forEach((d) => this.insert(d));
  }

  public async loadAll(): Promise<D[]> {
    return this.DB.asyncFind<D>({});
  }

  public async find(filter: any): Promise<D[]> {
    return this.DB.asyncFind<D>(filter);
  }
}
