import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/additive/';

class AdditiveService {

    create(expense) {
        return axios.post(API_URL + 'create', expense, {headers: authHeader()});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()})
    }

    load(id) {
        return axios.get(API_URL + `load/${id}`, {headers: authHeader()});
    }

    loadByContract(id) {
        return axios.get(API_URL + `loadByContract/${id}`, {headers: authHeader()});
    }

    remove(id) {
        return axios.get(API_URL + `remove/${id}`, {headers: authHeader()});
    }

}

export default new AdditiveService();