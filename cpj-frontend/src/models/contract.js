export  default class Contract {

    constructor(sigDate, durationType) {
        this.signatureDate = sigDate;
        this.durationType = durationType;
    }

    defaults() {
        return {
            number: '',
            description: '',
            signatureDate: '',
            durationType: '',
            duration: '',
            hired: [],
            contractors: [],
            paymentTypes: [],
            paymentValue: '',
            entryValue: '',
            endPercentValue: ''
        }
    }

}