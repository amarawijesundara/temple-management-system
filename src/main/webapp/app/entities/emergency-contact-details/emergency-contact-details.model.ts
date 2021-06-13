import * as dayjs from 'dayjs';
import { IPersonalDetails } from 'app/entities/personal-details/personal-details.model';
import { ContactType } from 'app/entities/enumerations/contact-type.model';

export interface IEmergencyContactDetails {
  id?: number;
  name?: string | null;
  address?: string | null;
  telephone?: number | null;
  email?: string | null;
  contactType?: ContactType | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  personalDetails?: IPersonalDetails | null;
}

export class EmergencyContactDetails implements IEmergencyContactDetails {
  constructor(
    public id?: number,
    public name?: string | null,
    public address?: string | null,
    public telephone?: number | null,
    public email?: string | null,
    public contactType?: ContactType | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public personalDetails?: IPersonalDetails | null
  ) {}
}

export function getEmergencyContactDetailsIdentifier(emergencyContactDetails: IEmergencyContactDetails): number | undefined {
  return emergencyContactDetails.id;
}
