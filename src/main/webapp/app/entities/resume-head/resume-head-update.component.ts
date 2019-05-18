import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IResumeHead, ResumeHead } from 'app/shared/model/resume-head.model';
import { ResumeHeadService } from './resume-head.service';

@Component({
  selector: 'jhi-resume-head-update',
  templateUrl: './resume-head-update.component.html'
})
export class ResumeHeadUpdateComponent implements OnInit {
  resumeHead: IResumeHead;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(32)]],
    mobile: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(16)]],
    email: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(32)]],
    age: [null, [Validators.required]],
    photo: [],
    residence: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(256)]]
  });

  constructor(protected resumeHeadService: ResumeHeadService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resumeHead }) => {
      this.updateForm(resumeHead);
      this.resumeHead = resumeHead;
    });
  }

  updateForm(resumeHead: IResumeHead) {
    this.editForm.patchValue({
      id: resumeHead.id,
      name: resumeHead.name,
      mobile: resumeHead.mobile,
      email: resumeHead.email,
      age: resumeHead.age,
      photo: resumeHead.photo,
      residence: resumeHead.residence
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const resumeHead = this.createFromForm();
    if (resumeHead.id !== undefined) {
      this.subscribeToSaveResponse(this.resumeHeadService.update(resumeHead));
    } else {
      this.subscribeToSaveResponse(this.resumeHeadService.create(resumeHead));
    }
  }

  private createFromForm(): IResumeHead {
    const entity = {
      ...new ResumeHead(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      mobile: this.editForm.get(['mobile']).value,
      email: this.editForm.get(['email']).value,
      age: this.editForm.get(['age']).value,
      photo: this.editForm.get(['photo']).value,
      residence: this.editForm.get(['residence']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResumeHead>>) {
    result.subscribe((res: HttpResponse<IResumeHead>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
