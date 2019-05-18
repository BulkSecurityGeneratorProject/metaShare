import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResumeHead } from 'app/shared/model/resume-head.model';
import { ResumeHeadService } from './resume-head.service';

@Component({
  selector: 'jhi-resume-head-delete-dialog',
  templateUrl: './resume-head-delete-dialog.component.html'
})
export class ResumeHeadDeleteDialogComponent {
  resumeHead: IResumeHead;

  constructor(
    protected resumeHeadService: ResumeHeadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.resumeHeadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'resumeHeadListModification',
        content: 'Deleted an resumeHead'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-resume-head-delete-popup',
  template: ''
})
export class ResumeHeadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ resumeHead }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ResumeHeadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.resumeHead = resumeHead;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/resume-head', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/resume-head', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
