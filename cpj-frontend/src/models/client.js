export default class Client {
    constructor(clientName, contactName, identityNumber, cpf, cnpj, clientType, contactPhoneOne, contactPhoneTwo, maritalStatus, citizenship, address) {
        this.clientName = clientName;
        this.contactName = contactName;
        this.identityNumber = identityNumber;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.clientType = clientType;
        this.contactPhoneOne = contactPhoneOne;
        this.contactPhoneTwo = contactPhoneTwo;
        this.maritalStatus = maritalStatus;
        this.citizenship = citizenship;
        this.address = address;
    }

    toString() {
        return this.clientName;
    }
}