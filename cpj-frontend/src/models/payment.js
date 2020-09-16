import Contract from "./contract";

export default class Payment {

    constructor(payment, contractId) {
        this.id = payment.id;
        this.description = payment.description;
        this.date = payment.date;
        this.paymentValue = payment.paymentValue;
        this.paymentType = payment.paymentType;
        this.contractId = contractId;
    }

    defaults() {
        return {
            id: null,
            description: '',
            date: new Date(),
            paymentValue: '',
            contractId: null,
            paymentType: '',
            contract: new Contract(),
            payed: false,
        }

    }
}