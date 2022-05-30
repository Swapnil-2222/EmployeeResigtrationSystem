import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ICourtCase, CourtCase } from '../court-case.model';
import { CourtCaseService } from '../service/court-case.service';

import { CourtCaseRoutingResolveService } from './court-case-routing-resolve.service';

describe('CourtCase routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: CourtCaseRoutingResolveService;
  let service: CourtCaseService;
  let resultCourtCase: ICourtCase | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(CourtCaseRoutingResolveService);
    service = TestBed.inject(CourtCaseService);
    resultCourtCase = undefined;
  });

  describe('resolve', () => {
    it('should return ICourtCase returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCourtCase = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCourtCase).toEqual({ id: 123 });
    });

    it('should return new ICourtCase if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCourtCase = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCourtCase).toEqual(new CourtCase());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as CourtCase })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultCourtCase = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultCourtCase).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
