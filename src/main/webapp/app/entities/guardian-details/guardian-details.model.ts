import * as dayjs from 'dayjs';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { ContactType } from 'app/entities/enumerations/contact-type.model';

export interface IGuardianDetails {
  id?: number;
  name?: string | null;
  address?: string | null;
  contactType?: ContactType | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  personalDetails?: IPersonalDetails | null;
}

export class GuardianDetails implements IGuardianDetails {
  constructor(
    public id?: number,
    public name?: string | null,
    public address?: string | null,
    public contactType?: ContactType | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public personalDetails?: IPersonalDetails | null
  ) {}
}

export function getGuardianDetailsIdentifier(guardianDetails: IGuardianDetails): number | undefined {
  return guardianDetails.id;
}
