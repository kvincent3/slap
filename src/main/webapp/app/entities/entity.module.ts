import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SlapRestoModule } from './resto/resto.module';
import { SlapClientModule } from './client/client.module';
import { SlapMenuModule } from './menu/menu.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SlapRestoModule,
        SlapClientModule,
        SlapMenuModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SlapEntityModule {}
