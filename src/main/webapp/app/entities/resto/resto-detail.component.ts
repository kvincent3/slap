import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Resto } from './resto.model';
import { RestoService } from './resto.service';

@Component({
    selector: 'jhi-resto-detail',
    templateUrl: './resto-detail.component.html'
})
export class RestoDetailComponent implements OnInit, OnDestroy {

    resto: Resto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private restoService: RestoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRestos();
    }

    load(id) {
        this.restoService.find(id).subscribe((resto) => {
            this.resto = resto;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRestos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'restoListModification',
            (response) => this.load(this.resto.id)
        );
    }
}
