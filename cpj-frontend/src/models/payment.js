import Contract from "./contract";

export default class Payment {

    constructor(payment, contractId) {
        this.description = payment.description;
        this.date = payment.date;
        this.paymentValue = payment.paymentValue;
        this.contractId = contractId;
    }

    defaults() {
        return {
            description: '',
            date: new Date(),
            paymentValue: '',
            contractId: 0,
            contract: new Contract(),
            payed: false,
        }

    }
}