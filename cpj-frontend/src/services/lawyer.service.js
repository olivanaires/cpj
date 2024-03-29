import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/lawyer/';

class LawyerService {
    create(lawyer) {
        return axios.post(API_URL + 'create', lawyer, {headers: authHeader()});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()})
    }

    load(id) {
        return axios.get(API_URL + `load/${id}`, {headers: authHeader()});
    }

    remove(id) {
        return axios.delete(API_URL + `remove/${id}`, {headers: authHeader()});
    }

}

export default new LawyerService();