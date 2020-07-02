import User from "./user";

export default class Lawyer {

    constructor() {
        this.name = '';
        this.oabNumber = '';
        this.user = new User('ROLE_LAWYER', '', '', '');
    }

}