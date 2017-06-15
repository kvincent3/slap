import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Menu } from './menu.model';
import { MenuService } from './menu.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-menu',
    templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit, OnDestroy {
menus: Menu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private menuService: MenuService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.menuService.query().subscribe(
            (res: ResponseWrapper) => {
                this.menus = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Menu) {
        return item.id;
    }
    registerChangeInMenus() {
        this.eventSubscriber = this.eventManager.subscribe('menuListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
