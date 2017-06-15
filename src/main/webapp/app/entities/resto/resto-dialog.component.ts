import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Resto } from './resto.model';
import { RestoPopupService } from './resto-popup.service';
import { RestoService } from './resto.service';

@Component({
    selector: 'jhi-resto-dialog',
    templateUrl: './resto-dialog.component.html'
})
export class RestoDialogComponent implements OnInit {

    resto: Resto;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private restoService: RestoService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.resto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.restoService.update(this.resto), false);
        } else {
            this.subscribeToSaveResponse(
                this.restoService.create(this.resto), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Resto>, isCreated: boolean) {
        result.subscribe((res: Resto) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Resto, isCreated: boolean) {
        this.alertService.success(
            isCreated ? `A new Resto is created with identifier ${result.id}`
            : `A Resto is updated with identifier ${result.id}`,
            null, null);

        this.eventManager.broadcast({ name: 'restoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-resto-popup',
    template: ''
})
export class RestoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private restoPopupService: RestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.restoPopupService
                    .open(RestoDialogComponent, params['id']);
            } else {
                this.modalRef = this.restoPopupService
                    .open(RestoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
