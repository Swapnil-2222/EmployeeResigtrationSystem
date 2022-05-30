export interface ICourtCase {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  address?: string | null;
  contactNo?: string | null;
  emailAddress?: string | null;
  salary?: string | null;
  gstOnSalary?: string | null;
  lastModified?: string | null;
}

export class CourtCase implements ICourtCase {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public address?: string | null,
    public contactNo?: string | null,
    public emailAddress?: string | null,
    public salary?: string | null,
    public gstOnSalary?: string | null,
    public lastModified?: string | null
  ) {}
}

export function getCourtCaseIdentifier(courtCase: ICourtCase): number | undefined {
  return courtCase.id;
}
