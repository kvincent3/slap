import { Menu } from '../menu';
export class Resto {
    constructor(
        public id?: number,
        public restoName?: string,
        public restoAddress?: string,
        public restoPhoneNumber?: string,
        public restomenus?: Menu,
    ) {
    }
}
