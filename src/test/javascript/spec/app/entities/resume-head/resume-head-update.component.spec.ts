/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { MetaShareTestModule } from '../../../test.module';
import { ResumeHeadUpdateComponent } from 'app/entities/resume-head/resume-head-update.component';
import { ResumeHeadService } from 'app/entities/resume-head/resume-head.service';
import { ResumeHead } from 'app/shared/model/resume-head.model';

describe('Component Tests', () => {
  describe('ResumeHead Management Update Component', () => {
    let comp: ResumeHeadUpdateComponent;
    let fixture: ComponentFixture<ResumeHeadUpdateComponent>;
    let service: ResumeHeadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetaShareTestModule],
        declarations: [ResumeHeadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResumeHeadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResumeHeadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResumeHeadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResumeHead(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ResumeHead();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
