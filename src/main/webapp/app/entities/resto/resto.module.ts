import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SlapSharedModule } from '../../shared';
import {
    RestoService,
    RestoPopupService,
    RestoComponent,
    RestoDetailComponent,
    RestoDialogComponent,
    RestoPopupComponent,
    RestoDeletePopupComponent,
    RestoDeleteDialogComponent,
    restoRoute,
    restoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...restoRoute,
    ...restoPopupRoute,
];

@NgModule({
    imports: [
        SlapSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RestoComponent,
        RestoDetailComponent,
        RestoDialogComponent,
        RestoDeleteDialogComponent,
        RestoPopupComponent,
        RestoDeletePopupComponent,
    ],
    entryComponents: [
        RestoComponent,
        RestoDialogComponent,
        RestoPopupComponent,
        RestoDeleteDialogComponent,
        RestoDeletePopupComponent,
    ],
    providers: [
        RestoService,
        RestoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlapRestoModule {}
