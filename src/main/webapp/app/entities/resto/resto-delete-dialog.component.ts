import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService, EventManager } from 'ng-jhipster';

import { Resto } from './resto.model';
import { RestoPopupService } from './resto-popup.service';
import { RestoService } from './resto.service';

@Component({
    selector: 'jhi-resto-delete-dialog',
    templateUrl: './resto-delete-dialog.component.html'
})
export class RestoDeleteDialogComponent {

    resto: Resto;

    constructor(
        private restoService: RestoService,
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.restoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'restoListModification',
                content: 'Deleted an resto'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success(`A Resto is deleted with identifier ${id}`, null, null);
    }
}

@Component({
    selector: 'jhi-resto-delete-popup',
    template: ''
})
export class RestoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private restoPopupService: RestoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.restoPopupService
                .open(RestoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
