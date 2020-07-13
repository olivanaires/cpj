export default class Contract {

    constructor(sigDate, durationType, paymentType) {
        this.description = 'HONORARY';
        this.signatureDate = sigDate;
        this.duration = 0;
        this.durationType = durationType;
        this.paymentType = paymentType;
        this.hired = [];
        this.contractors = [];
        this.endDate = sigDate;
        this.paymentDate = sigDate;
    }

    defaults() {
        return {
            number: '',
            description: '',
            signatureDate: '',
            endDate: '',
            paymentDate: '',
            durationType: '',
            duration: '',
            hired: [],
            contractors: [],
            // paymentTypes: [],
            paymentValue: '',
            entryValue: '',
            endPercentValue: '',
            observations: ''
        }
    }

}