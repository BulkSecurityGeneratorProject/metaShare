/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MetaShareTestModule } from '../../../test.module';
import { ResumeHeadDeleteDialogComponent } from 'app/entities/resume-head/resume-head-delete-dialog.component';
import { ResumeHeadService } from 'app/entities/resume-head/resume-head.service';

describe('Component Tests', () => {
  describe('ResumeHead Management Delete Component', () => {
    let comp: ResumeHeadDeleteDialogComponent;
    let fixture: ComponentFixture<ResumeHeadDeleteDialogComponent>;
    let service: ResumeHeadService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetaShareTestModule],
        declarations: [ResumeHeadDeleteDialogComponent]
      })
        .overrideTemplate(ResumeHeadDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResumeHeadDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResumeHeadService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
