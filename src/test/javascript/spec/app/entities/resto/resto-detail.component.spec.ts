import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { SlapTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RestoDetailComponent } from '../../../../../../main/webapp/app/entities/resto/resto-detail.component';
import { RestoService } from '../../../../../../main/webapp/app/entities/resto/resto.service';
import { Resto } from '../../../../../../main/webapp/app/entities/resto/resto.model';

describe('Component Tests', () => {

    describe('Resto Management Detail Component', () => {
        let comp: RestoDetailComponent;
        let fixture: ComponentFixture<RestoDetailComponent>;
        let service: RestoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SlapTestModule],
                declarations: [RestoDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RestoService,
                    EventManager
                ]
            }).overrideTemplate(RestoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RestoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestoService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Resto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.resto).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
