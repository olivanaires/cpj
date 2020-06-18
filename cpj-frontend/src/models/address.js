export default class Address {
    constructor(street, number, postalCode, city, neighborhood, complement) {
        this.postalCode = postalCode;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.complement = complement;
    }
}