import { Resto } from '../resto';
export class Menu {
    constructor(
        public id?: number,
        public starter?: string,
        public mainCourse?: string,
        public sweeties?: string,
        public beveries?: string,
        public resto?: Resto,
    ) {
    }
}
