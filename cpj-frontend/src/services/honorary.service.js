import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/honorary/';

class HonoraryService {

    create(expense) {
        return axios.post(API_URL + 'create', expense, {headers: authHeader()});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()})
    }

    listThisMonth() {
        return axios.get(API_URL + 'list-this-month', {headers: authHeader()})
    }

    load(id) {
        return axios.get(API_URL + `load/${id}`, {headers: authHeader()});
    }

    remove(id) {
        return axios.delete(API_URL + `remove/${id}`, {headers: authHeader()});
    }

}

export default new HonoraryService();