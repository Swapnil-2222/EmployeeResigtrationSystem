import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICourtCase, CourtCase } from '../court-case.model';

import { CourtCaseService } from './court-case.service';

describe('CourtCase Service', () => {
  let service: CourtCaseService;
  let httpMock: HttpTestingController;
  let elemDefault: ICourtCase;
  let expectedResult: ICourtCase | ICourtCase[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CourtCaseService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      firstName: 'AAAAAAA',
      lastName: 'AAAAAAA',
      address: 'AAAAAAA',
      contactNo: 'AAAAAAA',
      emailAddress: 'AAAAAAA',
      salary: 'AAAAAAA',
      gstOnSalary: 'AAAAAAA',
      lastModified: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a CourtCase', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new CourtCase()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CourtCase', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          address: 'BBBBBB',
          contactNo: 'BBBBBB',
          emailAddress: 'BBBBBB',
          salary: 'BBBBBB',
          gstOnSalary: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CourtCase', () => {
      const patchObject = Object.assign(
        {
          firstName: 'BBBBBB',
          contactNo: 'BBBBBB',
          gstOnSalary: 'BBBBBB',
        },
        new CourtCase()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CourtCase', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstName: 'BBBBBB',
          lastName: 'BBBBBB',
          address: 'BBBBBB',
          contactNo: 'BBBBBB',
          emailAddress: 'BBBBBB',
          salary: 'BBBBBB',
          gstOnSalary: 'BBBBBB',
          lastModified: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a CourtCase', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCourtCaseToCollectionIfMissing', () => {
      it('should add a CourtCase to an empty array', () => {
        const courtCase: ICourtCase = { id: 123 };
        expectedResult = service.addCourtCaseToCollectionIfMissing([], courtCase);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCase);
      });

      it('should not add a CourtCase to an array that contains it', () => {
        const courtCase: ICourtCase = { id: 123 };
        const courtCaseCollection: ICourtCase[] = [
          {
            ...courtCase,
          },
          { id: 456 },
        ];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, courtCase);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CourtCase to an array that doesn't contain it", () => {
        const courtCase: ICourtCase = { id: 123 };
        const courtCaseCollection: ICourtCase[] = [{ id: 456 }];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, courtCase);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCase);
      });

      it('should add only unique CourtCase to an array', () => {
        const courtCaseArray: ICourtCase[] = [{ id: 123 }, { id: 456 }, { id: 19289 }];
        const courtCaseCollection: ICourtCase[] = [{ id: 123 }];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, ...courtCaseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const courtCase: ICourtCase = { id: 123 };
        const courtCase2: ICourtCase = { id: 456 };
        expectedResult = service.addCourtCaseToCollectionIfMissing([], courtCase, courtCase2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(courtCase);
        expect(expectedResult).toContain(courtCase2);
      });

      it('should accept null and undefined values', () => {
        const courtCase: ICourtCase = { id: 123 };
        expectedResult = service.addCourtCaseToCollectionIfMissing([], null, courtCase, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(courtCase);
      });

      it('should return initial array if no CourtCase is added', () => {
        const courtCaseCollection: ICourtCase[] = [{ id: 123 }];
        expectedResult = service.addCourtCaseToCollectionIfMissing(courtCaseCollection, undefined, null);
        expect(expectedResult).toEqual(courtCaseCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
