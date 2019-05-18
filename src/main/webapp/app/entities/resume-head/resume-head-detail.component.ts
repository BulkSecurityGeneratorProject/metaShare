import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IResumeHead } from 'app/shared/model/resume-head.model';

@Component({
  selector: 'jhi-resume-head-detail',
  templateUrl: './resume-head-detail.component.html'
})
export class ResumeHeadDetailComponent implements OnInit {
  resumeHead: IResumeHead;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ resumeHead }) => {
      this.resumeHead = resumeHead;
    });
  }

  previousState() {
    window.history.back();
  }
}
