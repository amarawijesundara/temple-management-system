import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { AddressType } from 'app/entities/enumerations/address-type.model';

export interface IAddress {
  id?: number;
  type?: AddressType | null;
  address1?: string | null;
  address2?: string | null;
  postCode?: string | null;
  personalDetails?: IPersonalDetails | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public type?: AddressType | null,
    public address1?: string | null,
    public address2?: string | null,
    public postCode?: string | null,
    public personalDetails?: IPersonalDetails | null
  ) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
