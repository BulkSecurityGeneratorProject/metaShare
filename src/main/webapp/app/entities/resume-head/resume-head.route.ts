import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ResumeHead } from 'app/shared/model/resume-head.model';
import { ResumeHeadService } from './resume-head.service';
import { ResumeHeadComponent } from './resume-head.component';
import { ResumeHeadDetailComponent } from './resume-head-detail.component';
import { ResumeHeadUpdateComponent } from './resume-head-update.component';
import { ResumeHeadDeletePopupComponent } from './resume-head-delete-dialog.component';
import { IResumeHead } from 'app/shared/model/resume-head.model';

@Injectable({ providedIn: 'root' })
export class ResumeHeadResolve implements Resolve<IResumeHead> {
  constructor(private service: ResumeHeadService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IResumeHead> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ResumeHead>) => response.ok),
        map((resumeHead: HttpResponse<ResumeHead>) => resumeHead.body)
      );
    }
    return of(new ResumeHead());
  }
}

export const resumeHeadRoute: Routes = [
  {
    path: '',
    component: ResumeHeadComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ResumeHeads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ResumeHeadDetailComponent,
    resolve: {
      resumeHead: ResumeHeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ResumeHeads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ResumeHeadUpdateComponent,
    resolve: {
      resumeHead: ResumeHeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ResumeHeads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ResumeHeadUpdateComponent,
    resolve: {
      resumeHead: ResumeHeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ResumeHeads'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const resumeHeadPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ResumeHeadDeletePopupComponent,
    resolve: {
      resumeHead: ResumeHeadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ResumeHeads'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
