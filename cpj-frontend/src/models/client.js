import Address from "./address";

export default class Client {
    constructor(clientName, contactName, identityNumber, cpfCnpj, clientType, contactPhoneOne, contactPhoneTwo, maritalStatus, citizenship) {
        this.clientName = clientName;
        this.contactName = contactName;
        this.identityNumber = identityNumber;
        this.cpfCnpj = cpfCnpj;
        this.clientType = clientType;
        this.contactPhoneOne = contactPhoneOne;
        this.contactPhoneTwo = contactPhoneTwo;
        this.maritalStatus = maritalStatus;
        this.citizenship = citizenship;
        this.address = new Address();
    }

    toString() {
        return this.clientName;
    }
}