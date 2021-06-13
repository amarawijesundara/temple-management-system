import * as dayjs from 'dayjs';
import { IMonkDetails } from 'app/entities/monk-details/monk-details.model';
import { IAddress } from 'app/entities/address/address.model';
import { IEmergencyContactDetails } from 'app/entities/emergency-contact-details/emergency-contact-details.model';
import { IGuardianDetails } from 'app/entities/guardian-details/guardian-details.model';
import { Nationality } from 'app/entities/enumerations/nationality.model';
import { Ethnicity } from 'app/entities/enumerations/ethnicity.model';

export interface IPersonalDetails {
  id?: number;
  name?: string | null;
  gender?: string | null;
  nic?: string | null;
  nationality?: Nationality | null;
  ethnicity?: Ethnicity | null;
  passport?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  telephone?: number | null;
  mobile?: number | null;
  isBikshu?: boolean | null;
  isAnagarika?: boolean | null;
  isUpasaka?: boolean | null;
  notes?: string | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
  monkDetails?: IMonkDetails | null;
  addresses?: IAddress[] | null;
  emergencyCDS?: IEmergencyContactDetails[] | null;
  guardianDS?: IGuardianDetails[] | null;
}

export class PersonalDetails implements IPersonalDetails {
  constructor(
    public id?: number,
    public name?: string | null,
    public gender?: string | null,
    public nic?: string | null,
    public nationality?: Nationality | null,
    public ethnicity?: Ethnicity | null,
    public passport?: string | null,
    public dateOfBirth?: dayjs.Dayjs | null,
    public telephone?: number | null,
    public mobile?: number | null,
    public isBikshu?: boolean | null,
    public isAnagarika?: boolean | null,
    public isUpasaka?: boolean | null,
    public notes?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null,
    public monkDetails?: IMonkDetails | null,
    public addresses?: IAddress[] | null,
    public emergencyCDS?: IEmergencyContactDetails[] | null,
    public guardianDS?: IGuardianDetails[] | null
  ) {
    this.isBikshu = this.isBikshu ?? false;
    this.isAnagarika = this.isAnagarika ?? false;
    this.isUpasaka = this.isUpasaka ?? false;
  }
}

export function getPersonalDetailsIdentifier(personalDetails: IPersonalDetails): number | undefined {
  return personalDetails.id;
}
