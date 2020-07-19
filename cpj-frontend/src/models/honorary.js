import Contract from "./contract";

export default class Honorary {

    constructor(date) {
        this.description = '';
        this.date = date;
        this.paymentValue = '';
        this.contract = new Contract();
        this.parcels = 1;
    }

}