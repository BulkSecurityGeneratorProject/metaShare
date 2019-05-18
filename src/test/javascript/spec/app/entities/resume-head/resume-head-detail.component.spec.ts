/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MetaShareTestModule } from '../../../test.module';
import { ResumeHeadDetailComponent } from 'app/entities/resume-head/resume-head-detail.component';
import { ResumeHead } from 'app/shared/model/resume-head.model';

describe('Component Tests', () => {
  describe('ResumeHead Management Detail Component', () => {
    let comp: ResumeHeadDetailComponent;
    let fixture: ComponentFixture<ResumeHeadDetailComponent>;
    const route = ({ data: of({ resumeHead: new ResumeHead(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetaShareTestModule],
        declarations: [ResumeHeadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResumeHeadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResumeHeadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.resumeHead).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
