import AsyncNedb from 'nedb-async';

export class Document {
  private type = '';

  public setType(type: string) {
    this.type = type;
  }

  public getType(): string {
    return this.type;
  }
}

function loadDb(): AsyncNedb<unknown> {
  const db = new AsyncNedb({
    filename: 'C:/Users/Daniel/Desktop/nedb.data',
    autoload: true,
  });
  return db;
}

const DB: AsyncNedb<any> = loadDb();

export function insert(doc: Document) {
  const type = (doc as any).constructor.name;
  doc.setType(type);
  DB.insert(doc);
}

export function load(typeName: string) {
  return DB.asyncFind({ type: typeName });
}
