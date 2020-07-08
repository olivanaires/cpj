import Contract from "./contract";

export default class Expense {

    constructor() {
        this.description = '';
        this.date = new Date();
        this.paymentValue = '';
        this.contract = new Contract();
        this.refundable = true;
    }

}