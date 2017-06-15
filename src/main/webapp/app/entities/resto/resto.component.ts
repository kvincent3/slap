import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Resto } from './resto.model';
import { RestoService } from './resto.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-resto',
    templateUrl: './resto.component.html'
})
export class RestoComponent implements OnInit, OnDestroy {
restos: Resto[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private restoService: RestoService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.restoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.restos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRestos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Resto) {
        return item.id;
    }
    registerChangeInRestos() {
        this.eventSubscriber = this.eventManager.subscribe('restoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
