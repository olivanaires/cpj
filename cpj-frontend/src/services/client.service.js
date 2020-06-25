import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/client/';

class ClientService {

    create(client) {
        return axios.post(API_URL + 'create', client, {headers: authHeader()});
    }

    list() {
        return axios.get(API_URL + 'list', {headers: authHeader()})
    }

}

export default new ClientService();