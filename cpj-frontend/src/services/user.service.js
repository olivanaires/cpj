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
}

export default new UserService();