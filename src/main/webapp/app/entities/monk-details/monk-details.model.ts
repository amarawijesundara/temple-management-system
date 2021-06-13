import * as dayjs from 'dayjs';

export interface IMonkDetails {
  id?: number;
  nameEnglish?: string | null;
  nameSinhala?: string | null;
  pawidiNo?: string | null;
  samaneraNo?: string | null;
  upasampadaNo?: string | null;
  pawidiDate?: dayjs.Dayjs | null;
  upasampadaDate?: dayjs.Dayjs | null;
  createdDate?: dayjs.Dayjs | null;
  updatedDate?: dayjs.Dayjs | null;
}

export class MonkDetails implements IMonkDetails {
  constructor(
    public id?: number,
    public nameEnglish?: string | null,
    public nameSinhala?: string | null,
    public pawidiNo?: string | null,
    public samaneraNo?: string | null,
    public upasampadaNo?: string | null,
    public pawidiDate?: dayjs.Dayjs | null,
    public upasampadaDate?: dayjs.Dayjs | null,
    public createdDate?: dayjs.Dayjs | null,
    public updatedDate?: dayjs.Dayjs | null
  ) {}
}

export function getMonkDetailsIdentifier(monkDetails: IMonkDetails): number | undefined {
  return monkDetails.id;
}
