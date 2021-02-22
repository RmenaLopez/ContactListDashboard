export class Contact {

  public constructor(init?: Partial<Contact>) {
    Object.assign(this, init);
  }
  id: bigint;
  contactName: string;
  age: string;
  nickname: string;
  phone: string;
}
