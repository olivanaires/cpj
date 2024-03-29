import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/contract/';

class ContractService {
    create(contract) {
        return axios.post(API_URL + 'create', contract, {headers: authHeader()});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()})
    }

    listWithHonorary() {
        return axios.get(API_URL + 'list-with-honorary', {headers: authHeader()})
    }

    load(id) {
        return axios.get(API_URL + `load/${id}`, {headers: authHeader()});
    }

    remove(id) {
        return axios.delete(API_URL + `remove/${id}`, {headers: authHeader()});
    }

    paymentsThisMonth() {
        return  axios.get(API_URL + 'list/with-payment-this-month', {headers: authHeader()});
    }

    endDateThisMonth() {
        return  axios.get(API_URL + 'list/with-end-date-this-month', {headers: authHeader()});
    }

    loadPayments(id) {
        return  axios.get(API_URL + `list/payments/${id}`, {headers: authHeader()});
    }

    receivePayment(payment) {
        return  axios.post(API_URL + 'receve-payment', payment, {headers: authHeader()});
    }

}

export default new ContractService();
