import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITax, Tax } from 'app/shared/model/tax.model';
import { TaxService } from './tax.service';

@Component({
  selector: 'jhi-tax-update',
  templateUrl: './tax-update.component.html'
})
export class TaxUpdateComponent implements OnInit {
  tax: ITax;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    provinceCode: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(8)]],
    name: [],
    rate: []
  });

  constructor(protected taxService: TaxService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tax }) => {
      this.updateForm(tax);
      this.tax = tax;
    });
  }

  updateForm(tax: ITax) {
    this.editForm.patchValue({
      id: tax.id,
      provinceCode: tax.provinceCode,
      name: tax.name,
      rate: tax.rate
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tax = this.createFromForm();
    if (tax.id !== undefined) {
      this.subscribeToSaveResponse(this.taxService.update(tax));
    } else {
      this.subscribeToSaveResponse(this.taxService.create(tax));
    }
  }

  private createFromForm(): ITax {
    const entity = {
      ...new Tax(),
      id: this.editForm.get(['id']).value,
      provinceCode: this.editForm.get(['provinceCode']).value,
      name: this.editForm.get(['name']).value,
      rate: this.editForm.get(['rate']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITax>>) {
    result.subscribe((res: HttpResponse<ITax>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
