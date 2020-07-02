import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/user/';

class UserService {
    getPublicContent() {
        return axios.get('/api/test/all', {headers: authHeader()});
    }

    create(user) {
        return axios.post(API_URL + 'create', user, {headers: authHeader()});
    }

    update(username, password) {
        return axios.post(API_URL + 'update', {
            username,
            password
        }, {headers: authHeader()});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()})
    }

    resetPassword(email) {
        return axios.get(API_URL + 'passwordRefresh/' + email);
    }

    load(id) {
        return axios.get(API_URL + `load/${id}`, {headers: authHeader()});
    }
}

export default new UserService();