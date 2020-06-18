import axios from 'axios';
import authHeader from './auth-header';

const API_URL = '/api/client/';

class ClientService {

    create(client) {
        console.log(client)
        return axios.post(API_URL + 'create', client, {headers: authHeader()});
    }

}

export default new ClientService();