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
        this.signatureEndDate = sigDate;
        this.paymentDate = sigDate;
    }

    defaults() {
        return {
            number: '',
            description: '',
            signatureDate: '',
            signatureEndDate: '',
            endDate: '',
            paymentDate: '',
            durationType: '',
            duration: '',
            hired: [],
            contractors: [],
            additives: [],
            paymentSignatureValue: '',
            paymentValue: '',
            entryValue: '',
            endPercentValue: '',
            observations: '',
            fileId: '',
        }
    }

}