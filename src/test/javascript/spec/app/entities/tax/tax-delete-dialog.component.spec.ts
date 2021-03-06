/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MetaShareTestModule } from '../../../test.module';
import { TaxDeleteDialogComponent } from 'app/entities/tax/tax-delete-dialog.component';
import { TaxService } from 'app/entities/tax/tax.service';

describe('Component Tests', () => {
  describe('Tax Management Delete Component', () => {
    let comp: TaxDeleteDialogComponent;
    let fixture: ComponentFixture<TaxDeleteDialogComponent>;
    let service: TaxService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetaShareTestModule],
        declarations: [TaxDeleteDialogComponent]
      })
        .overrideTemplate(TaxDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxService);
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
