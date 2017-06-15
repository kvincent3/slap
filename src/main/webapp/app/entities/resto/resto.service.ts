import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Resto } from './resto.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RestoService {

    private resourceUrl = 'api/restos';

    constructor(private http: Http) { }

    create(resto: Resto): Observable<Resto> {
        const copy = this.convert(resto);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(resto: Resto): Observable<Resto> {
        const copy = this.convert(resto);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Resto> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(resto: Resto): Resto {
        const copy: Resto = Object.assign({}, resto);
        return copy;
    }
}
