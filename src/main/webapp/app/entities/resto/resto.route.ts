import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RestoComponent } from './resto.component';
import { RestoDetailComponent } from './resto-detail.component';
import { RestoPopupComponent } from './resto-dialog.component';
import { RestoDeletePopupComponent } from './resto-delete-dialog.component';

import { Principal } from '../../shared';

export const restoRoute: Routes = [
    {
        path: 'resto',
        component: RestoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Restos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'resto/:id',
        component: RestoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Restos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const restoPopupRoute: Routes = [
    {
        path: 'resto-new',
        component: RestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Restos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resto/:id/edit',
        component: RestoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Restos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resto/:id/delete',
        component: RestoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Restos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
