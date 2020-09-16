import Contract from "./contract";

export default class Expense {

    constructor(date) {
        this.description = '';
        this.date = date;
        this.paymentValue = '';
        this.contract = new Contract();
        this.refundable = true;
    }

    defaults() {
        return {
            description: '',
            date: new Date(),
            paymentValue: '',
            contract: new Contract(),
            refundable: false,
            payed: false,
        }
    }


}